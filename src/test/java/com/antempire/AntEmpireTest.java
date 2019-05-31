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
        Cell right = new Cell(5, 0);

        FactHandle centerFH = session.insert(center);
        FactHandle rightFH = session.insert(right);

        int firstFire = session.fireAllRules();
        assertEquals(2, firstFire);
        System.out.println();

        Ant worker = new Worker();
        center.setAnt(worker);
        session.insert(worker);
        session.update(centerFH, center);

        int workerRules = session.fireAllRules();
        assertEquals(4, workerRules);
        System.out.println();

        Ant queen1 = new Queen();
        right.setAnt(queen1);
        session.insert(queen1);
        session.update(rightFH, right);

        int queenFire = session.fireAllRules();
        assertEquals(4, queenFire);
        System.out.println();

        Ant queen2 = new Queen();
        center.setAnt(queen2);
        session.insert(queen2);
        session.update(centerFH, center);

        int queenFire2 = session.fireAllRules();
        assertEquals(4, queenFire2);
        System.out.println();

    }
}
