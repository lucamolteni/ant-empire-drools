package com.antempire;

import java.util.List;
import java.util.UUID;

import org.drools.modelcompiler.ExecutableModelProject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.*;

public abstract class BaseModelTest {

    protected KieSession getKieSession(String... rules) {
        KieFile[] stringRules1 = toKieFiles(rules);
        KieServices ks = KieServices.get();
        ReleaseId releaseId = ks.newReleaseId("org.kie", "kjar-test-" + UUID.randomUUID(), "1.0");

        ks.getRepository().removeKieModule(releaseId);

        KieFileSystem kfs = ks.newKieFileSystem();
        String pom =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "\n" +
                "  <groupId>" + releaseId.getGroupId() + "</groupId>\n" +
                "  <artifactId>" + releaseId.getArtifactId() + "</artifactId>\n" +
                "  <version>" + releaseId.getVersion() + "</version>\n" +
                "</project>";
        kfs.writePomXML(pom);
        for (KieFile kieFile : stringRules1) {
            kfs.write(kieFile.path, kieFile.content);
        }

        KieBuilder kieBuilder1 = ks.newKieBuilder(kfs).buildAll(ExecutableModelProject.class);

        List<Message> messages = kieBuilder1.getResults().getMessages();
        if (!messages.isEmpty()) {
            fail(messages.toString());
        }

        return ks.newKieContainer(releaseId).newKieSession();
    }

    public static class KieFile {

        public final String path;
        public final String content;

        public KieFile(int index, String content) {
            this(String.format("src/main/resources/r%d.drl", index), content);
        }

        public KieFile(String path, String content) {
            this.path = path;
            this.content = content;
        }
    }

    public KieFile[] toKieFiles(String[] stringRules) {
        KieFile[] kieFiles = new KieFile[stringRules.length];
        for (int i = 0; i < stringRules.length; i++) {
            kieFiles[i] = new KieFile(i, stringRules[i]);
        }
        return kieFiles;
    }

}
