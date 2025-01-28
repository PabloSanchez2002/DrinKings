<script setup lang="ts">
import { Card, CardHeader, CardTitle, CardDescription, CardContent, CardFooter } from '@/components/ui/card';

</script>

<template>
    <div class="landing flex flex-col items-center p-6">
        <!-- Sección de Imagen -->
        <Card class="mb-8 w-full max-w-4xl">
            <CardHeader>
                <CardTitle>
                    <h1 class="text-3xl font-bold">Bienvenido a DrinKings</h1>
                    <img src="/homeImage.jpg" alt="Imagen de Inicio"
                        class="home-image w-60 rounded-3xl shadow-md mx-auto my-4" />
                </CardTitle>
                <CardDescription>
                    <p class="text-lg">
                        DrinKings es tu compañero definitivo para descubrir y compartir increíbles recetas de bebidas.
                        Ya sea
                        que seas un mixólogo experimentado o estés empezando, nuestra aplicación tiene algo para todos.
                    </p>
                </CardDescription>
            </CardHeader>
            <CardContent>
                <h2 class="text-2xl font-semibold">Cómo usar la aplicación</h2>
                <ol class="list-decimal list-inside mt-4 space-y-2">
                    <li>Explora nuestra amplia colección de recetas de bebidas.</li>
                    <li>Guarda tus recetas favoritas en tu colección personal.</li>
                    <li>Comparte tus propias recetas de bebidas con la comunidad.</li>
                    <li>Califica y comenta las bebidas que has probado.</li>
                </ol>
            </CardContent>
            <CardFooter>
                <p class="text-sm text-gray-600">
                    ¿Listo para empezar? ¡Regístrate o inicia sesión para comenzar a explorar!
                </p>
            </CardFooter>
        </Card>

        <!-- Sección de Introducción -->
        <div class="introduction text-center mt-8 space-y-4">

        </div>

        <!-- Sección de Pasos -->
        <div class="steps mt-12 w-full max-w-4xl text-left">

        </div>
    </div>
</template>