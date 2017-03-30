<%--
  #%L
  GarethHealy :: Game of Life :: Frontend
  %%
  Copyright (C) 2013 - 2017 Gareth Healy
  %%
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  #L%
  --%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!--[if IE 9]><html lang="en-us" class="ie9"><![endif]-->
<!--[if gt IE 9]><!-->
<html lang="en-us">
<!--<![endif]-->
    <head lang="en">
        <spring:url value="/resources/bower_components/patternfly/dist/img/favicon.ico" var="favicon" />
        <spring:url value="/resources/bower_components/patternfly/dist/css/patternfly.min.css" var="patternflyCss" />
        <spring:url value="/resources/bower_components/patternfly/dist/css/patternfly-additions.min.css" var="patternflyAdditionsCss" />
        <spring:url value="/resources/bower_components/patternfly/components/jquery/dist/jquery.js" var="jqueryJs" />
        <spring:url value="/resources/bower_components/patternfly/components/bootstrap/dist/js/bootstrap.js" var="bootstrapJs" />
        <spring:url value="/resources/bower_components/patternfly/components/c3/c3.min.js" var="c3Js" />
        <spring:url value="/resources/bower_components/patternfly/components/d3/d3.min.js" var="d3Js" />

        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="${favicon}">

        <!-- PatternFly Styles -->
        <!-- Note: No other CSS files are needed regardless of what other JS packages located in patternfly/components that you decide to pull in -->
        <link rel="stylesheet" href="${patternflyCss}" charset="utf-8" />
        <link rel="stylesheet" href="${patternflyAdditionsCss}" charset="utf-8" />

        <!-- jQuery -->
        <script src="${jqueryJs}" charset="utf-8"></script>

        <!-- Bootstrap JS -->
        <script src="${bootstrapJs}" charset="utf-8"></script>

        <!-- C3, D3 - Charting Libraries -->
        <script src="${c3Js}" charset="utf-8"></script>
        <script src="${d3Js}" charset="utf-8"></script>

        <!-- Timer -->
        <script src="https://raw.githubusercontent.com/jchavannes/jquery-timer/master/jquery.timer.js" charset="utf-8"></script>

        <title>Game Of Life</title>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-pf" role="navigation">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse-1">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
          </div>
          <div class="collapse navbar-collapse navbar-collapse-1">
            <ul class="nav navbar-nav navbar-primary">
              <li class="active">
                <a href="/reset" class="active">Home</a>
              </li>
              <li>
                <a href="/reset">Reset</a>
              </li>
            </ul>
          </div>
        </nav>

        <div class="container-fluid">
          <div class="row">
            <div class="col-md-12">
              <ol class="breadcrumb">
                <li><a href="#">Home</a></li>
                <li>Game Of Life</li>
              </ol>
              <h1>Game Of Life</h1>
              <div id="gof"/>
            </div><!-- /col -->
          </div><!-- /row -->
        </div><!-- /container -->

        <script charset="utf-8">
            $(document).ready(function () {
                var timer = $.timer(function () {
                    $.get("tick", function (response) {
                        drawBoard(response);

                        board.append("svg:rect")
                            .data(convertToD3Model(response));
                    });
                });

                function drawBoard(cellsResponse) {
                    if (cellsResponse) {
                        var rows = [];
                        for (var y = 0; y < cellsResponse.height; y++) {
                            var row = [];
                            for (var x = 0; x < cellsResponse.width; x++) {
                                var cell = $.grep(cellsResponse.cells, function (value, index) {
                                    return x === value.xCords && y === value.yCords;
                                })[0];

                                var representation = '0';
                                if (cell.state === 'ALIVE') {
                                    representation = '<strong style="color:red"><span style="display:none">x' + cell.xCords + '/ y' + cell.yCords + '</span>1</strong>';
                                }

                                row.push(representation);
                            }

                            rows.push(row.join(' | '));
                        }

                        $('#gof').html(rows.join("</br>"));
                    }
                }

                timer.set({
                    time: 1000,
                    autostart: true
                });
            });
        </script>
    </body>
</html>
