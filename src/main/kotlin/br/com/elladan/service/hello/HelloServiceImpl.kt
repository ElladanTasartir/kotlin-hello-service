package br.com.elladan.service.hello

class HelloServiceImpl: HelloService {
    override fun sayHello(name: String): String = "Hello, $name"
}