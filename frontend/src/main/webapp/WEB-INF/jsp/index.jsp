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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js" charset="utf-8"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.min.js" charset="utf-8"></script>
        <script src="https://d3js.org/d3.v3.min.js" charset="utf-8"></script>
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
                        var cellsResponse = $.parseJSON(response);
                        drawBoard(cellsResponse);

                        board.append("svg:rect")
                                .data(convertToD3Model(cellsResponse));
                    });
                });

                function drawBoard(cellsResponse) {
                    if (cellsResponse) {
                        var rows = [];
                        for (var y = 0; y < cellsResponse.size; y++) {
                            var row = [];
                            for (var x = 0; x < cellsResponse.size; x++) {
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
