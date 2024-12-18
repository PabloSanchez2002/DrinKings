<script setup lang="ts">
import { Button } from '@/components/ui/button'
import {
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { useToast } from '@/components/ui/toast/use-toast'
import { Card, CardDescription, CardContent, CardHeader, CardTitle } from '@/components/ui/card'

import { LoaderCircle } from 'lucide-vue-next'
import { toTypedSchema } from '@vee-validate/zod'
import { useForm } from 'vee-validate'
import { useRouter } from 'vue-router'
import * as z from 'zod'
import apiClient from '@/services/apiClient'
import { ref } from 'vue'

const isLoading = ref(false)
const { toast } = useToast()

// Define validation schema
const formSchema = toTypedSchema(
    z.object({
        email: z.string().email({ message: 'Introduce un email válido' }),
        password: z.string().min(6, { message: 'La contraseña debe tener al menos 6 caracteres' }),
    })
)

// Use form with validation
const { isFieldDirty, handleSubmit } = useForm({
    validationSchema: formSchema,
})
const router = useRouter()

// Submit handler
const onSubmit = handleSubmit(async (values) => {
    isLoading.value = true

    // Create Basic Auth header
    const credentials = btoa(`${values.email}:${values.password}`)
    const config = {
        headers: {
            Authorization: `Basic ${credentials}`,
        },
    }

    apiClient.get('/user/login', config)
        .then((response) => {
            if (response.status === 200) {
                localStorage.setItem('auth_token', response.data.token)
                toast({
                    title: '¡Bienvenido!',
                    description: 'Inicio de sesión exitoso.',
                })
                router.push('/home') // Adjust the route as needed
            } else {
                toast({
                    title: 'Error',
                    description: response.data.message || 'Error al iniciar sesión',
                    variant: 'destructive',
                })
            }
        })
        .catch((error) => {
            toast({
                title: 'Error',
                description: error.response?.data?.message || error.response?.data || 'No se pudo iniciar sesión. Inténtalo de nuevo.',
                variant: 'destructive',
            })
        })
        .finally(() => {
            isLoading.value = false
        })
})
</script>

<template>
    <Card class="mx-auto max-w-sm w-4/5">
        <CardHeader>
            <CardTitle class="text-2xl">
                Login
            </CardTitle>
            <CardDescription>
                Inicia sesión con tu correo electrónico
            </CardDescription>
        </CardHeader>
        <CardContent>
            <form @submit="onSubmit" class="w-90 mx-auto space-y-4">
                <!-- Email Field -->
                <FormField v-slot="{ componentField }" name="email" :validate-on-blur="!isFieldDirty">
                    <FormItem>
                        <FormLabel>Email</FormLabel>
                        <FormControl>
                            <Input type="email" placeholder="email@example.com" v-bind="componentField" />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                </FormField>

                <!-- Password Field -->
                <FormField v-slot="{ componentField }" name="password" :validate-on-blur="!isFieldDirty">
                    <FormItem>
                        <FormLabel>Contraseña</FormLabel>
                        <FormControl>
                            <Input type="password" placeholder="********" v-bind="componentField" />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                </FormField>

                <!-- Submit Button -->
                <Button type="submit" class="w-full" :disabled="isLoading">
                    <LoaderCircle v-if="isLoading" class="w-5 h-5 mr-2" />
                    Login
                </Button>

                <!-- Forgot Password & Signup -->
                <div class="mt-4 text-center text-sm">
                    <a href="#" class="underline">¿Olvidaste tu contraseña?</a>
                    <br />
                    ¿No tienes cuenta?
                    <router-link class="underline" to="signup" color="foreground">
                        Crear una cuenta
                    </router-link>
                </div>
            </form>
        </CardContent>
    </Card>
</template>
