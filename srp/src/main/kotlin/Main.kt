import com.nimbusds.srp6.SRP6ClientSession
import com.nimbusds.srp6.SRP6CryptoParams
import com.nimbusds.srp6.SRP6ServerSession
import com.nimbusds.srp6.SRP6VerifierGenerator
import java.math.BigInteger


fun main(args: Array<String>) {
    // N = 125617018995153554710546479714086468244499594888726646874671447258204721048803 (2*q+1, q - prime)
    // g = 2
    // H = SHA-256
    val config: SRP6CryptoParams = SRP6CryptoParams.getInstance(256, "SHA-256")

    // Регистрация нового пользователя
    val login = "login"
    val password = "password"

    val verifierGenerator = SRP6VerifierGenerator(config)
    val salt = BigInteger(verifierGenerator.generateRandomSalt(16)) // 16 бит
    val verifier = verifierGenerator.generateVerifier(salt, password) // v = g^x (mod N)
    /*
    public BigInteger generateVerifier(final BigInteger salt, final String userID, final String password) {

		byte[] userIDBytes = null;

		if (userID != null)
			userIDBytes = userID.getBytes(Charset.forName("UTF-8"));

		byte[] passwordBytes = password.getBytes(Charset.forName("UTF-8"));

		byte[] saltBytes = BigIntegerUtils.bigIntegerToBytes(salt);

		return generateVerifier(saltBytes, userIDBytes, passwordBytes);
	}

	public BigInteger generateVerifier(final byte[] salt, final byte[] password) {

		return generateVerifier(salt, null, password);
	}
     */

    println("s: " + salt.toString(16))
    println("v: " + verifier.toString(16))

    // Вход пользователя
    val clientSession = SRP6ClientSession()
    clientSession.step1(login, password)

    // Сервер. Шаг 1. Поиск пользователя в БД, вычисление B
    val serverSession = SRP6ServerSession(config)
    val B = serverSession.step1(login, salt, verifier)
    /*
    b = random()
    B = kv + gb, где k = H(N, g)
     */

    println("s: " + salt.toString(16))
    println("B: " + B.toString(16))

    // Клиент. Шаг 2. Вычисление A и M1
    val credentials = clientSession.step2(config, salt, B)
    val A = credentials.A // генерация случайного числа 'a', а также вычисляется публичное A = g^a
    val M1 = credentials.M1 // M1 = H(A | B | SC)

    println("A: " + A.toString(16))
    println("M1: " + M1.toString(16))
    println("Client session key: " + clientSession.sessionKey.toString(16))

    // Сервер. Шаг 2. Вычисление M2
    val M2 = serverSession.step2(A, M1) // M2 = H(A | M1 | SS) (опционально)

    println("M2: " + M2.toString(16))
    println("Server session key: " + serverSession.sessionKey.toString(16))
}