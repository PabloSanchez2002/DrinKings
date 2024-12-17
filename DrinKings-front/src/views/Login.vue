<script setup lang="ts">
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
</script>

<template>
    <Card class="mx-auto max-w-sm">
        <CardHeader>
            <CardTitle class="text-2xl">
                Login
            </CardTitle>
            <CardDescription>
                Inicia sesión con tu correo electrónico
            </CardDescription>
        </CardHeader>
        <CardContent>
            <div class="grid gap-4">
                <div class="grid gap-2">
                    <Label for="email">Email</Label>
                    <Input id="email" type="email" placeholder="m@example.com" required />
                </div>
                <div class="grid gap-2">
                    <div class="flex items-center">
                        <Label for="password">Contraseña</Label>
                        <a href="#" class="ml-auto inline-block text-sm underline">
                            ¿Olvidates tu contraseña?
                        </a>
                    </div>
                    <Input id="password" type="password" required />
                </div>
                <Button type="submit" class="w-full">
                    Login
                </Button>
            </div>
            <div class="mt-4 text-center text-sm">
                ¿No tienes cuenta?
                <router-link class="underline" to="signup" color="foreground">
                    Crear una cuenta
                </router-link>
            </div>
        </CardContent>
    </Card>
</template>