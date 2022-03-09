package com.y9vad9.construct.ksp

import com.construct.annotation.ViewDSL
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated

class ConstructSymbolProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    private var evaluated = false
    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        if(evaluated)
            return emptyList()
        evaluated = true
        resolver.getAllFiles().filter { it.isAnnotationPresent(ViewDSL::class) }.forEach {
            it.accept(ViewDSLVisitor(environment.codeGenerator), Unit)
        }
        return emptyList()
    }
}