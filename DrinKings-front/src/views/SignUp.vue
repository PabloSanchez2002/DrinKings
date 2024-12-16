<script setup lang="ts">
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
</script>

<template>

    <Card class="mx-auto max-w-sm">
        <CardHeader>
            <CardTitle class="text-2xl">
                Create una cuenta
            </CardTitle>
            <CardDescription>
                Registrate para acceder a DrinKings!
            </CardDescription>
        </CardHeader>
        <CardContent>
            <div class="grid gap-4">
                <div class="grid gap-2">
                    <Label for="email">Email</Label>
                    <Input id="email" type="email" placeholder="email@example.com" required />
                </div>
                <div class="grid grid-cols-2 gap-4">
                    <div class="grid gap-2">
                        <Label for="userame">Nombre de usuario</Label>
                        <Input id="userame" placeholder="ManoloGarcía" required />
                    </div>
                    <div class="grid gap-2">
                        <Label for="password">Contraseña</Label>
                        <Input id="password" type="password" required />
                    </div>
                </div>

                <Button type="submit" class="w-full">
                    Create an account
                </Button>
            </div>
            <div class="mt-4 text-center text-sm">
                Ya tienes cuesta?
                <a href="#" class="underline">
                    Inicia sesión
                </a>
            </div>
        </CardContent>
    </Card>
</template>