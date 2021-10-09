var canvas = document.getElementById('graph');
var ctx = canvas.getContext('2d');
var div = 18;
var kf = canvas.height / div;
var offset = canvas.height / 2;
var areaCanvas = document.getElementById('area');
var areaCtx = areaCanvas.getContext('2d');

function drawArea(r) {
    areaCtx.clearRect(0, 0, areaCanvas.width, areaCanvas.height);
    var expr = math.compile('2*sqrt(-abs(abs(x)-1)*abs(3-abs(x))/((abs(x)-1)*(3-abs(x))))(1+abs(abs(x)-3)/(abs(x)-3))sqrt(1-(x/7)^2)+(5+0.97(abs(x-.5)+abs(x+.5))-3(abs(x-.75)+abs(x+.75)))(1+abs(1-abs(x))/(1-abs(x)))')
    let xValues = math.range(0, 1, 0.05).toArray()
    let yValues = xValues.map(function (x) {
        return expr.evaluate({x: x})
    })

    expr = math.compile('((2.71052+(1.5-.5abs(x))-1.35526sqrt(4-(abs(x)-1)^2))sqrt(abs(abs(x)-1)/(abs(x)-1)))');
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
    areaCtx.moveTo(xValues[0]*r * kf + offset, -yValues[0]*r * kf + offset);
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i]*r * kf + offset, -yValues[i]*r * kf + offset);
    }
    for(let i = 0; i < xValues.length; i++) {
        xValues[i] = -xValues[i];
    }
    for(let i = 0; i < xValues.length; i++) {
        areaCtx.lineTo(xValues[i]*r * kf + offset, -yValues[i]*r * kf + offset);
    }
    areaCtx.closePath;
    areaCtx.fillStyle = "#55c3e5";
    areaCtx.fill();
}

function draw(r) {
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
    drawArea(r);
}

function clickSetup() {
    $("#graph").click(function (e) {
        let rect = canvas.getBoundingClientRect();
        let x = e.clientX - rect.left;
        let y = e.clientY - rect.top;
        x = (x / kf - (div / 2));
        y = -(y / kf - (div / 2));
        drawByJS([{name:'x', value: x}, {name:'y', value: y}]);
    });
}

function drawDotByClick(res, xD, yD) {
    drawDot(xD * kf + offset, -yD * kf + offset, res, 3);
}

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
    ctx.beginPath();
    ctx.arc(x, y, size, 0, Math.PI * 2, true);
    ctx.fill();
}