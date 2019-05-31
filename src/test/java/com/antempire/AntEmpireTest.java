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

        KieSession session = getKieSession(rules);

        Cell center = new Cell(4, 4);
        Cell right = new Cell(5, 0);

        session.insert(center);

        int firstFire = session.fireAllRules();
        assertEquals(1, firstFire);
        System.out.println();

        Ant worker = new Worker();
        center.setAnt(worker);
        session.insert(worker);

        int workerRules = session.fireAllRules();
        assertEquals(3, workerRules);
        System.out.println();

        Ant queen1 = new Queen();
        right.setAnt(queen1);
        session.insert(queen1);
        session.insert(right);

        int queenFire = session.fireAllRules();
        assertEquals(4, queenFire);
        System.out.println();

    }
}
