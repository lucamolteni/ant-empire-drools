package com.antempire;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import static org.junit.Assert.*;

public class AntEmpireTest extends BaseModelTest {

    @Test
    public void testKJar() throws Exception{

        URL resource = getClass().getResource("/com/antempire/rules.drl");
        Path path = Paths.get(resource.toURI());
        final String rules = new String(Files.readAllBytes(path));

        KieSession session = getKieSession(rules);

        Cell center = new Cell(4, 4);
        Cell right = new Cell(5, 4);

        FactHandle centerFH = session.insert(center);
        FactHandle rightFH = session.insert(right);

        session.fireAllRules();
        System.out.println();

        // Center produces with a worker
        Ant worker = new Worker();
        center.setAnt(worker);
        session.insert(worker);
        session.update(centerFH, center);

        session.fireAllRules();
        System.out.println();
        assertTrue(center.isProducing());

        // Right produces with a queen
        Ant queen1 = new Queen();
        right.setAnt(queen1);
        session.insert(queen1);
        session.update(rightFH, right);

        session.fireAllRules();
        assertTrue(right.isProducing());
        System.out.println();

        // Two adjacent queen stops production
        Ant queen2 = new Queen();
        center.setAnt(queen2);
        session.insert(queen2);
        session.update(centerFH, center);

        session.fireAllRules();
        assertFalse(center.isProducing());
        assertFalse(right.isProducing());
        System.out.println();

    }
}
