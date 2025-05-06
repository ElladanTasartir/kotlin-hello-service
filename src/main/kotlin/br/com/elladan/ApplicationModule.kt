package br.com.elladan

import br.com.elladan.service.hello.HelloService
import br.com.elladan.service.hello.HelloServiceImpl
import com.google.inject.AbstractModule

class ApplicationModule : AbstractModule() {
    override fun configure() {
        bind(HelloService::class.java).to(HelloServiceImpl::class.java)
    }
}