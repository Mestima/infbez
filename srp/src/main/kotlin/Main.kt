import com.nimbusds.srp6.SRP6ClientSession
import com.nimbusds.srp6.SRP6CryptoParams
import com.nimbusds.srp6.SRP6ServerSession
import com.nimbusds.srp6.SRP6VerifierGenerator
import java.math.BigInteger


fun main(args: Array<String>) {
    val config: SRP6CryptoParams = SRP6CryptoParams.getInstance(256, "SHA-1")

    // Регистрация нового пользователя
    val login = "login"
    val password = "password"

    val verifierGenerator = SRP6VerifierGenerator(config)
    val salt = BigInteger(verifierGenerator.generateRandomSalt(16))
    val verifier = verifierGenerator.generateVerifier(salt, password)

    println("s: " + salt.toString(16))
    println("v: " + verifier.toString(16))

    // Вход пользователя
    val clientSession = SRP6ClientSession()
    clientSession.step1(login, password)

    // Сервер. Шаг 1. Поиск пользователя в БД, вычисление B
    val serverSession = SRP6ServerSession(config)
    val B = serverSession.step1(login, salt, verifier)

    println("s: " + salt.toString(16))
    println("B: " + B.toString(16))

    // Клиент. Шаг 2. Вычисление A и M1
    val credentials = clientSession.step2(config, salt, B)
    val A = credentials.A
    val M1 = credentials.M1

    println("A: " + A.toString(16))
    println("M1: " + M1.toString(16))
    println("Client session key: " + clientSession.sessionKey.toString(16))

    // Сервер. Шаг 2. Вычисление M2
    val M2 = serverSession.step2(A, M1)

    println("M2: " + M2.toString(16))
    println("Server session key: " + serverSession.sessionKey.toString(16))
}