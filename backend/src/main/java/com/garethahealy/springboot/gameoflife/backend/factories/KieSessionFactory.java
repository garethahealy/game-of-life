/*
 * #%L
 * GarethHealy :: Game of Life :: Backend
 * %%
 * Copyright (C) 2013 - 2015 Gareth Healy
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.garethahealy.springboot.gameoflife.backend.factories;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class KieSessionFactory {

    private KieContainer kContainer;

    private Set<KieSession> statefulSessions = new HashSet<KieSession>();

    @PostConstruct
    public synchronized void start() {
        KieServices kServices = KieServices.Factory.get();
        kContainer = kServices.newKieClasspathContainer(); //getKieClasspathContainer();
    }

    @PreDestroy
    public synchronized void stop() {
        for (KieSession session : statefulSessions) {
            session.dispose();
        }

        statefulSessions.clear();
    }

    public synchronized StatelessKieSession getStatelessSession() {
        if (kContainer == null) {
            throw new IllegalStateException("kContainer is null. Have you called start?");
        }

        return kContainer.newStatelessKieSession();
    }

    public synchronized KieSession getStatefulSession() {
        if (kContainer == null) {
            throw new IllegalStateException("kContainer is null. Have you called start?");
        }

        KieSession session = kContainer.newKieSession();
        statefulSessions.add(session);

        return session;
    }

    public synchronized void disposeOf(KieSession session) {
        if (session != null) {
            statefulSessions.remove(session);

            session.dispose();
        }
    }
}
