let buttonContainer = document.querySelector(".button-container");

const TestObj = (function () {
    function TestObj() {
        this.testArr = [];
    }
    TestObj.prototype.testJs = function (maxItem) {
        console.log("test-js");
        for (let i = 0; i < maxItem; i++) this.testArr.push(i);

        console.log(`array length: ${this.testArr.length}`);
    };

    TestObj.prototype.resetJs = function () {
        console.log("reset-js");
        this.testArr = [];
                console.log(this.testArr);

        console.log(`array length: ${this.testArr.length}`);
    };
    TestObj.prototype.testDom = function () {
        console.log("test-dom");
    };

    TestObj.prototype.resetDom = function () {
        console.log("reset-dom");
    };

    return TestObj;
} ());


const testObj = new TestObj();

buttonContainer.addEventListener("click", (e) => {
    const target = e.target.id;
    switch (target) {
        case "test-js":
            testObj.testJs(200000);
            break;
        case "reset-js":
            testObj.resetJs();
            break;
        case "test-dom":
            testObj.testDom();
            break;
        case "reset-dom":
            testObj.resetDom();
            break;
    }
});