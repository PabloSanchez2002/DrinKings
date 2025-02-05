<script setup lang="ts">
import { Card, CardHeader, CardTitle, CardDescription, CardContent, CardFooter } from '@/components/ui/card';
import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from "@/components/ui/accordion"
import { Button } from '@/components/ui/button'
import { CircleUser, Beer, LoaderCircle } from 'lucide-vue-next'

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
                        DrinKings es aplicación para competir por la corona de rey de la bebida.
                    </p>
                </CardDescription>
            </CardHeader>
            <CardContent>
                <Accordion type="single" collapsible>
                    <AccordionItem value="item-1">
                        <AccordionTrigger>¿Cómo se usa la aplicación?</AccordionTrigger>
                        <AccordionContent>
                            <ol class="list-decimal list-inside mt-4 space-y-2">
                                <li>Haz click en el boton de la esquina superior izquierda </li>
                                <Button variant="outline" size="sm" class="shrink-0 h-10 w-10">
                                    <Beer class="h-8 w-8" />
                                </Button>
                                <li>Crea o únete a una liga con tus amigos.</li>
                                ( •ᴗ•)⸝🍻⸜(•ᴗ• )
                                <li>Anota las bebidas que consumas (no hagas trampas!).</li>
                                <li>Dale al botón de
                                    <Button variant="default" size="sm" class="shrink-0 h-6 w-14">
                                        Guardar
                                    </Button>.
                                </li>
                                <li>Las puntuaciones se actualizan, y se muestran según pasa el tiempo</li>
                            </ol>
                        </AccordionContent>
                    </AccordionItem>
                    <AccordionItem value="item-2">
                        <AccordionTrigger>¿Cómo crear/unirme a una liga?</AccordionTrigger>
                        <AccordionContent>
                            <ol class="list-decimal list-inside mt-4 space-y-2">
                                <li>Haz click en el boton de la esquina superior izquierda </li>
                                <Button variant="outline" size="sm" class="shrink-0 h-10 w-10">
                                    <Beer class="h-8 w-8" />
                                </Button>
                                <li>Haz click en en crear o unete a una liga. 🍻</li>

                                <li>Rellena el formulario (si te quieres unir a una liga, pídele el código a alguien).
                                </li>
                                <li>Todo listo!</li>
                            </ol>
                        </AccordionContent>
                    </AccordionItem>
                    <!-- <AccordionItem value="item-3">
                        <AccordionTrigger>¿Como se usa la aplicación?</AccordionTrigger>
                        <AccordionContent>
                            Yes. It adheres to the WAI-ARIA design pattern.
                        </AccordionContent>
                    </AccordionItem> -->
                </Accordion>

            </CardContent>
            <CardFooter>
                <p class="text-sm text-gray-600">
                    ¿Listo para empezar? ¡Unete a una liga y compite!
                </p>
            </CardFooter>
        </Card>

        <!-- Sección de Introducción -->
        <div class="introduction text-center mt-8 space-y-4">

        </div>

        <!-- Sección de Pasos -->
        <div class="text-sm text-gray-600 steps mt-12 w-full max-w-4xl text-center">
            Version 1.0.0
        </div>
    </div>
</template>