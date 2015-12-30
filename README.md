[![Build Status](https://travis-ci.org/garethahealy/game-of-life.svg)](https://travis-ci.org/garethahealy/game-of-life)

# game-of-life
- http://en.wikipedia.org/wiki/Conway's_Game_of_Life
- http://trillions.maya.com/game_of_life/

# Tech
## Web
- https://projects.spring.io/spring-boot
- https://www.patternfly.org
- https://github.com/jchavannes/jquery-timer
- https://jquery.com

## Package Management
- https://www.npmjs.com
- http://www.bower.io

# Useful links
- https://spring.io/guides/gs/spring-boot/
- http://docs.spring.io/spring-boot/docs/1.3.1.RELEASE/reference/htmlsingle/#getting-started-installing-the-cli

# Build
- mvn clean install

## Spring Boot
### Build for Spring Boot
- cd frontend && mvn package -Pspringboot

### Run with Spring Boot
- cd frontend && mvn spring-boot:run

## Build for EAP
- cd frontend && mvn package -Peap

# URLS
- http://localhost:8080/
- http://localhost:8080/reset
- http://localhost:8080/tick

# TODO
- http://d3js.org
-- draw a "board" via d3 and different colours for alive/dead cells
- add unit tests via arquillian for BRMS
