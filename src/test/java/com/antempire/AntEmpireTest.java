package com.antempire;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.*;

public class AntEmpireTest {

    @Test
    public void testKJar() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("AntEmpireKBase.session");


        Cell center = new Cell(4, 4);
        Cell right = new Cell(5, 0);

        kSession.insert(center);
        kSession.insert(right);

        int rules = kSession.fireAllRules();
        assertEquals(2, rules);
    }
}
