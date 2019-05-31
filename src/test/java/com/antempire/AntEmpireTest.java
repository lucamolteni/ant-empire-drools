package com.antempire;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.*;

public class AntEmpireTest extends BaseModelTest {

    @Test
    public void testKJar() throws Exception{

        URL resource = getClass().getResource("/com/antempire/rules.drl");
        Path path = Paths.get(resource.toURI());
        final String rules = new String(Files.readAllBytes(path));

        KieSession kSession = getKieSession(rules);

        Cell center = new Cell(4, 4);
        Cell right = new Cell(5, 0);

        kSession.insert(right);

//        int rules = kSession.fireAllRules();
//        assertEquals(2, rules);

        center.setAnt(new Worker());
        kSession.insert(center);
        int workerRules = kSession.fireAllRules();

        assertEquals(3, workerRules);


    }
}
