<%--
  #%L
  GarethHealy :: Game of Life :: Frontend
  %%
  Copyright (C) 2013 - 2015 Gareth Healy
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
<html>
    <head lang="en">
        <meta charset="utf-8">
        <spring:url value="/resources/bower_components/patternfly/dist/css/patternfly.min.css" var="patternflyCss" />
        <spring:url value="/resources/bower_components/patternfly/dist/css/patternfly-additions.min.css" var="patternflyAdditionsCss" />
        <spring:url value="/resources/bower_components/patternfly/components/jquery/dist/jquery.js" var="jqueryJs" />
        <spring:url value="/resources/bower_components/patternfly/components/bootstrap/dist/js/bootstrap.js" var="bootstrapJs" />
        <spring:url value="/resources/bower_components/patternfly/components/c3/c3.min.js" var="c3Js" />
        <spring:url value="/resources/bower_components/patternfly/components/d3/d3.min.js" var="d3Js" />

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
        <script charset="utf-8">
            $(document).ready(function () {
                var board = d3.select("#board")
                        .append("svg:svg")
                        .attr("width", 100)
                        .attr("height", 100);

                board.append("svg:rect")
                        .attr("x", 0)
                        .attr("y", 0)
                        .attr("width", "10")
                        .attr("height", "10")
                        .style("fill", "");

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
                                    representation = '<strong><span style="display:none">x' + cell.xCords + '/ y' + cell.yCords + '</span>1</strong>';
                                }

                                row.push(representation);
                            }

                            rows.push(row.join(' | '));
                        }

                        $('#gof').html(rows.join("</br>"));
                    }
                }

                function convertToD3Model(cellsResponse) {
                    var data = [];
                    for (var i = 0; i < cellsResponse.cells.length; i++) {
                        var current = cellsResponse.cells[i];

                        var d3Model = {
                            "x_axis": current.xCords,
                            "y_axis": current.yCords,
                            "width": 10,
                            "height": 10,
                            "color": "#424242"
                        };

                        data.push(d3Model);
                    }

                    return d3Model;
                }

                timer.set({
                    time: 1000,
                    autostart: true
                });
            });
        </script>
        <title>Game Of Life</title>
    </head>
    <body>
        <h3>${message}</h3>
        <div id="gof">${gof}</div>
        <div id="board"></div>
    </body>
</html>
