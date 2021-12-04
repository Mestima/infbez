const DEBUG = true;
var log = console.log;

Array.prototype.random = function () {
  return this[Math.floor((Math.random()*this.length))];
}

var mod = (divident, divisor) => {
	return divident % divisor;
};

var trueRandom = (min, max) => {
	min = Math.ceil(min);
	max = Math.floor(max);
	return Math.floor(Math.random() * (max - min + 1)) + min;
};

var generate = (n, f) => {
	var old = [];
	var res = [];
	for (var i = 1; i < n; i++) old.push([i, true]);

	for (var i = 2; i*i < n; i++) {
		if (old[i-1][1])
			for (var x = i*i; x < n; x++) {
				if (x % i == 0) old[x-1][1] = false;
				if (f && x % f == 0) old[x-1][1] = false;
			}
	}

	old.forEach((v) => {
		if (v[1]) res.push(v[0]);
	});
	return res;
};

var f = (p, q) => {
	return (p-1)*(q-1);
};

var isValid = (a, b) => {
	return (a % b) != 0 && (b % a) != 0;
};

/*
var pack = (str) => {
	var result = Uint8Array.from(Buffer.from(str));
	//log(result);
	return result;
}

var unpack = (bytes) => {
	var result = new Buffer.from(bytes).toString();
	//log(result);
	return result;
}

var encrypt = (msg, key) => {
	var m = pack(msg);
	log(m);
	var arr = [];
	for (var i = 0; i < m.length; i++) {
		arr.push(mod((m[i] ** key.k), key.m));
	}
	return arr.reverse().join();
}

var decrypt = (msg, key) => {
	var m = msg.split(',').reverse();
	var arr = [];
	for (var i = 0; i < m.length; i++) {
		arr.push(mod((m[i] ** key.k), key.m));
	}
	log(arr);
	var res = unpack(arr);
	return msg;
}
*/

var encrypt = (msg, key) => {
	return BigInt(mod(msg ** key.k, key.m));
}

var decrypt = (msg, key) => {
	return BigInt(mod(msg ** key.k, key.m));
}

var errCount = 0;
var getKey = () => {
	var res = generate(12); // если увеличить степень, получим переполнение
	var p = res.random();
	var q = res.random();
	while (p == q) {
		q = res.random();
	}
	var m = p * q;

	// 1 < e < f(p,q), простое, взаимно простое с f(p, q)
	// {e, m} - открытый ключ
	var fi = f(p, q);
	var e = res.random();
	var err = 0;
	while ((e < 1 || e > fi) && !isValid(e, fi)) {
		e = res.random();
		err++;
		if (err > 100) {
			if (DEBUG) {
				errCount++;
				console.log("ERROR: попытки сгенерировать нормальное число тщетны... Перезапуск алгоритма. [1]["+errCount+"]");
			}		
			return false;
		}
	}
	var openKey = { k: BigInt(e), m: BigInt(m) };

	// (d*e) mod f(p, q) = 1
	var d = trueRandom(2, 110);
	err = 0;
	while ((d*e) % fi != 1) {
		d = trueRandom(2, 110);
		err++;
		if (err > 500) {
			if (DEBUG) {
				errCount++;
				console.log("ERROR: попытки сгенерировать нормальное число тщетны... Перезапуск алгоритма. [2]["+errCount+"]");
			}
			return false;
		}
	}
	var privateKey = { k: BigInt(d), m: BigInt(m) };
	return { o: openKey, p: privateKey };
}

var aliceKey;
while (!aliceKey) {
	aliceKey = getKey();
}
console.log(`Ключи Алисы:\nприватный - {${aliceKey.p.k}, ${aliceKey.p.m}}\nоткрытый - {${aliceKey.o.k}, ${aliceKey.o.m}}\n`);

var toCrypt = BigInt(3);
log(`Шифруем число: ${toCrypt}`);
var en = encrypt(toCrypt, aliceKey.o);
log(`Зашифровано: ${en}`);
var de = decrypt(en, aliceKey.p);
log(`Расшифровано: ${de}`);
