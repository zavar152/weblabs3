canvas = document.getElementById('graph');
ctx = canvas.getContext('2d');
div = 18;
kf = canvas.height / div;
offset = canvas.height / 2;
const areaCanvas = document.getElementById('area');
const areaCtx = areaCanvas.getContext('2d');

function resetAll() {
    resetArea();
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    draw();
}

function resetArea() {
    radiusGlobal = undefined;
    areaCtx.clearRect(0, 0, areaCanvas.width, areaCanvas.height);
}

function drawArea(r) {
    console.log('Area r: ' + r);
    areaCtx.clearRect(0, 0, areaCanvas.width, areaCanvas.height);
    var expr = math.compile('2*sqrt(-abs(abs(x)-1)*abs(3-abs(x))/((abs(x)-1)*(3-abs(x))))(1+abs(abs(x)-3)/(abs(x)-3))sqrt(1-(x/7)^2)+(5+0.97(abs(x-.5)+abs(x+.5))-3(abs(x-.75)+abs(x+.75)))(1+abs(1-abs(x))/(1-abs(x)))')
    let xValues = math.range(0, 1, 0.05).toArray()
    let yValues = xValues.map(function (x) {
        return expr.evaluate({x: x})
    })

    expr = math.compile('((2.71052+(1.5-.5abs(x))-1.35526sqrt(4-(abs(x)-1)^2))sqrt(abs(abs(x)-1)/(abs(x)-1))+0.9)');
    var tempX = (math.range(0, 3, 0.05).toArray());
    var tempY = (tempX.map(function (x) {
        return expr.evaluate({x: x})
    }));
    xValues = xValues.concat(tempX);
    yValues = yValues.concat(tempY);

    expr = math.compile('7 * sqrt( 1 - y^2 / 9)');
    tempY = (math.range(3, 0, -0.05).toArray());
    tempX = (tempY.map(function (y) {
        return expr.evaluate({y: y})
    }));

    let tempXz = [];
    let tempYz = [];

    for(let i = 0; i < tempX.length; i++) {
       if(tempX[i] > 3) {
           tempXz.push(tempX[i]);
           tempYz.push(tempY[i]);
       }
    }

    xValues = xValues.concat(tempXz);
    yValues = yValues.concat(tempYz);

    expr = math.compile('-3sqrt(1-(x/7)^2)sqrt(abs(abs(x)-4)/(abs(x)-4))');
    tempX = math.range(7, 4, -0.05).toArray();
    tempY = (tempX.map(function (x) {
        return expr.evaluate({x: x})
    }));

    xValues = xValues.concat(tempX);
    yValues = yValues.concat(tempY);

    expr = math.compile('abs(x/2)-((3*sqrt(33)-7)/(112))*(x^2)-3+sqrt(1-(abs(abs(x)-2)-1)^2)');
    tempX = math.range(4, 0, -0.05).toArray();
    tempY = (tempX.map(function (x) {
        return expr.evaluate({x: x})
    }));

    xValues = xValues.concat(tempX);
    yValues = yValues.concat(tempY);



    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        //console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    for(let i = 0; i < xValues.length; i++) {
        xValues[i] = -xValues[i];
    }
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        //console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.fillStyle = "#55c3e5";
    areaCtx.fill();
}

function seg1() {
    areaCtx.clearRect(0, 0, areaCanvas.width, areaCanvas.height);
    const expr = math.compile('2*sqrt(-abs(abs(x)-1)*abs(3-abs(x))/((abs(x)-1)*(3-abs(x))))(1+abs(abs(x)-3)/(abs(x)-3))sqrt(1-(x/7)^2)+(5+0.97(abs(x-.5)+abs(x+.5))-3(abs(x-.75)+abs(x+.75)))(1+abs(1-abs(x))/(1-abs(x)))')
    xValues = math.range(0, 1, 0.05).toArray()
    yValues = xValues.map(function (x) {
        return expr.evaluate({x: x})
    })
    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.stroke();

    for(let i = 0; i < xValues.length; i++) {
        xValues[i] = -xValues[i];
    }

    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.stroke();
}

function seg2() {
    const expr = math.compile('abs(x/2)-((3*sqrt(33)-7)/(112))*(x^2)-3+sqrt(1-(abs(abs(x)-2)-1)^2)');
    xValues = math.range(0, 4, 0.05).toArray()
    yValues = xValues.map(function (x) {
        return expr.evaluate({x: x})
    })
    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.stroke();

    for(let i = 0; i < xValues.length; i++) {
        xValues[i] = -xValues[i];
    }

    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.stroke();
}

function seg3() {
    const expr = math.compile('(2.71052+(1.5-.5abs(x))-1.35526sqrt(4-(abs(x)-1)^2))sqrt(abs(abs(x)-1)/(abs(x)-1))+0.9');
    xValues = math.range(0, 3, 0.05).toArray()
    yValues = xValues.map(function (x) {
        return expr.evaluate({x: x})
    })
    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.stroke();

    for(let i = 0; i < xValues.length; i++) {
        xValues[i] = -xValues[i];
    }

    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.stroke();
}

function seg4() {
    const expr = math.compile('-3sqrt(1-(x/7)^2)sqrt(abs(abs(x)-4)/(abs(x)-4))');
    xValues = math.range(4, 7, 0.05).toArray()
    yValues = xValues.map(function (x) {
        return expr.evaluate({x: x})
    })
    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.stroke();

    for(let i = 0; i < xValues.length; i++) {
        xValues[i] = -xValues[i];
    }

    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.stroke();
}

function seg5() {
    const expr = math.compile('7 * sqrt( 1 - y^2 / 9)');
    yValues = math.range(0, 3, 0.05).toArray()
    xValues = yValues.map(function (y) {
        return expr.evaluate({y: y})
    })
    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.stroke();

    for(let i = 0; i < xValues.length; i++) {
        xValues[i] = -xValues[i];
    }

    areaCtx.beginPath();
    areaCtx.moveTo(xValues[0] * kf + offset, -yValues[0] * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i] * kf + offset, -yValues[i] * kf + offset);
        console.log('X: ' + xValues[i] + " Y: " + yValues[i])
    }
    areaCtx.closePath;
    areaCtx.stroke();
}

function draw() {
    ctx.strokeRect(0, 0, canvas.height, canvas.width);
    ctx.beginPath();
    ctx.moveTo(canvas.width / 2, 0);
    ctx.lineTo(canvas.width / 2, canvas.height);
    ctx.closePath();
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(0, canvas.height / 2);
    ctx.lineTo(canvas.width, canvas.height / 2);
    ctx.closePath();
    ctx.stroke();

    drawDot(canvas.width / 2, canvas.height / 2, "#000000", 5);
    drawSegmentX(0, div);
    drawSegmentY(0, div);
    clickSetup();
    drawArea(1);
}

function clickSetup() {
    $("#graph").click(function (e) {

        drawByJS([{name:'x', value:1}, {name:'y', value:1}]);
    });
}

function drawDotByClick(res, xD, yD) {
        drawDot(xD * kf + offset, -yD * kf + offset, "#37f863", 3);
        console.log('X: ' + xD * kf + offset)
        console.log('Y: ' + yD * kf + offset)
        //drawDot(xD * kf + offset, -yD * kf + offset, "#d02020", 3);
}

// function drawDotAtClick(event) {
//     var rect = canvas.getBoundingClientRect();
//     var x = event.clientX - rect.left;
//     var y = event.clientY - rect.top;
//
//     drawDot(x, y, "#ce49f3", 3);
//     let body = "x=" + (x / kf - (div / 2)) + "&y=" + -(y / kf - (div / 2)) + "&r=" + radiusGlobal;
// }

function drawSegmentX(beginFromX, n) {
    ctx.font = "14px serif";
    for (let i = 0; i <= n; i++) {
        ctx.beginPath();
        ctx.moveTo(beginFromX + kf * i, (canvas.height / 2) + 5);
        ctx.lineTo(beginFromX + kf * i, (canvas.height / 2) - 5);
        ctx.closePath();
        ctx.stroke();
        if (Math.abs(i - div / 2) !== div / 2) {
            if (i - div / 2 === 0)
                ctx.fillText(i - div / 2, beginFromX + kf * i + 5, (canvas.height / 2) + 15);
            else if (i - div / 2 < 0)
                ctx.fillText(i - div / 2, beginFromX + kf * i + 3, (canvas.height / 2) + 15);
            else if (i - div / 2 > 0)
                ctx.fillText(i - div / 2, beginFromX + kf * i + 1, (canvas.height / 2) + 15);
        }
    }
}

function drawSegmentY(beginFromY, n) {
    ctx.font = "14px serif";
    for (let i = 0; i <= n; i++) {
        ctx.beginPath();
        ctx.moveTo((canvas.height / 2) - 5, kf * i);
        ctx.lineTo((canvas.height / 2) + 5, kf * i);
        ctx.closePath();
        ctx.stroke();
        if (Math.abs(i - div / 2) !== div / 2) {
            if (i - div / 2 < 0)
                ctx.fillText(-(i - div / 2), (canvas.height / 2) + 10, beginFromY + kf * i + 3);
            else if (i - div / 2 > 0)
                ctx.fillText(-(i - div / 2), (canvas.height / 2) - 20, beginFromY + kf * i + 3);
        }
    }
}

function drawDot(x, y, color, size) {
    ctx.fillStyle = color;
    console.log('x: ' + x);
    console.log('y: ' + y);

    console.log('true x: ' + (x / kf - (div / 2)));
    console.log('true y: ' + -(y / kf - (div / 2)));

    ctx.beginPath();
    ctx.arc(x, y, size, 0, Math.PI * 2, true);
    ctx.fill();
}

var radiusGlobal;

function resetCheckBox(element) {
    let reset = true;
    document.getElementsByName('r').forEach((value) => {
        if (!value.isEqualNode(element) && value.checked) {
            value.checked = !value.checked;
        }
        if (value.isEqualNode(element)) {
            radiusGlobal = value.value;
            drawArea(value.value);
            if (!value.checked) {
                radiusGlobal = undefined;
                resetArea();
            }
        }
    });
}

function check() {
    const r = radiusGlobal;
    const x = document.forms["inForm"]["x"].value;
    const y = document.forms["inForm"]["y"].value;

    console.log('Got x: ' + x);
    console.log('Got y: ' + y);
    console.log('Got r: ' + r);

    if (x !== '' && !isNaN(x) && (x >= -3 && x <= 5)) {
        if (y !== '' && !isNaN(y) && !isNegativeZero(Number(y)) && (y >= -3 && y <= 5)) {
            if (!isNaN(r) && (r == 1 || r == 1.5 || r == 2 || r == 2.5 || r == 3)) {
                drawDot(x * kf + offset, -y * kf + offset, "#ce49f3", 3);
                return true;
            } else {
                alert("Выберите корректное значение R");
                return false;
            }
        } else {
            alert("Введите корректное значение Y (-3..5)");
            return false;
        }
    } else {
        alert("Выберите корректное значение X");
        return false;
    }
}

function isNegativeZero(p) {
    return p === 0 && (1 / p) === -Infinity
}