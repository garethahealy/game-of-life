[![Build Status](https://travis-ci.org/garethahealy/game-of-life.svg)](https://travis-ci.org/garethahealy/game-of-life)

# game-of-life
- http://en.wikipedia.org/wiki/Conway's_Game_of_Life
- http://trillions.maya.com/game_of_life/

# Tech
- http://projects.spring.io/spring-boot/
- https://github.com/jchavannes/jquery-timer
- http://jquery.com

- https://www.npmjs.com
- https://www.patternfly.org
- http://d3js.org
- http://requirejs.org

# Useful links
- https://spring.io/guides/gs/spring-boot/
- http://docs.spring.io/spring-boot/docs/1.3.1.RELEASE/reference/htmlsingle/#getting-started-installing-the-cli

# Build
- mvn clean install

## Build for Spring Boot
- cd frontend && mvn package -Pspringboot

## Build for EAP
- cd frontend && mvn package -Peap

## Run with Spring Boot
- cd frontend && mvn spring-boot:run

# URLS
- http://localhost:8080/tick

# TODO
- add require.js
- draw a "board" via d3
- draw different colours for alive/dead cells via d3
- change data returned to be json, so d3 can accept (x/y and state)
- add unit tests
