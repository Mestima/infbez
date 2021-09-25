Array.prototype.random = function () {
  return this[Math.floor((Math.random()*this.length))];
}

var trueRandom = (min, max) => {
	min = Math.ceil(min);
	max = Math.floor(max);
	return Math.floor(Math.random() * (max - min + 1)) + min;
};

var generate = (n) => {
	var old = [];
	var res = [];
	for (var i = 1; i < n; i++) old.push([i, true]);

	for (var i = 2; i*i < n; i++) {
		if (old[i-1][1])
			for (var x = i*i; x < n; x++) {
				if (x % i == 0) old[x-1][1] = false;
			}
	}

	old.forEach((v) => {
		if (v[1]) res.push(v[0]);
	});
	return res;
};

console.log("Алиса и Боб выбирают числа...");
var p = trueRandom(2, 51);
var g = trueRandom(52, 102);
console.log("Алиса и Боб выбрали числа.\nАлиса и Боб сообщают числа друг другу:\nМодуль: "+p+" \nГенератор: "+g+"\nЕва, имея злой умысел, перехватывает эти числа.");

console.log("\nАлиса и Боб выбирают секретные простые числа...");
var res = generate(12); // если увеличить степень, получим переполнение
var a = res.random();
var b = res.random();
console.log("Секретное простое число Алисы: "+a+"\nСекретное простое число Боба: "+b);

var aliceGen = Math.pow(g, a) % p;
var bobGen = Math.pow(g, b) % p;
console.log("Алиса возводит генератор в степень своего секретного числа, получает остаток от него и отправляет Бобу.");
console.log("Боб возводит генератор в степень своего секретного числа, получает остаток от него и отправляет Алисе.");
console.log("\nАлиса получила: "+aliceGen+"\nБоб получил: "+bobGen);
console.log("\nЕва перехватывает оба эти числа.\n");

console.log("Алиса восстанавливает секретный ключ, возводя полученное от Боба число в степень своего секретного числа и взяв остаток.")
console.log("Боб восстанавливает секретный ключ, возводя полученное от Боба число в степень своего секретного числа и взяв остаток.")

var aliceKey = Math.pow(bobGen, a) % p;
var bobKey = Math.pow(aliceGen, b) % p;

console.log("\nАлиса получила секретный ключ: "+aliceKey);
console.log("\nБоб получил секретный ключ: "+bobKey+"\n");

if (aliceKey == bobKey)
	console.log("Как мы видим, их ключи совпали. Алиса и Боб успешно передали друг другу секретный ключ, а Ева сидит и не может ничего понять, так как не обладает секретными простыми числами Алисы и Боба. Еве остаётся только подбирать бесчисленное количество раз.");
else
	console.log("Ого, Алиса и Боб не смогли передать друг другу секретный ключ. Они не умеют считать цифорки.");
