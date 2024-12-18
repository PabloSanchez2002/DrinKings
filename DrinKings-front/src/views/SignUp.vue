<script setup lang="ts">
import { Button } from '@/components/ui/button'
import {
    FormControl,
    FormDescription,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { useToast } from '@/components/ui/toast/use-toast'
import { Card, CardDescription, CardContent, CardHeader, CardTitle } from '@/components/ui/card'

import { LoaderCircle } from 'lucide-vue-next';
import { toTypedSchema } from '@vee-validate/zod'
import { useForm } from 'vee-validate'
// import { h } from 'vue'
import { useRouter } from 'vue-router'
import * as z from 'zod'
import apiClient from '@/services/apiClient'
import { ref } from 'vue'

const isLoading = ref(false)
const { toast } = useToast()

// Define el esquema de validación con zod
const formSchema = toTypedSchema(
    z.object({
        email: z.string().email({ message: 'Introduce un email válido' }),
        username: z.string().min(2, { message: 'El nombre de usuario debe tener al menos 2 caracteres' }),
        password: z.string().min(6, { message: 'La contraseña debe tener al menos 6 caracteres' }),
    })
)

// Usa el hook `useForm` con la validación
const { isFieldDirty, handleSubmit } = useForm({
    validationSchema: formSchema,
})
const router = useRouter()

// Acción al enviar el formulario
const onSubmit = handleSubmit(async (values) => {
    isLoading.value = true
    apiClient.post('/user/register', values)
        .then((response) => {
            // console.log(response)
            console.log(response.status)
            if (response.status === 200) {
                toast({
                    title: '¡Bienvenido!',
                    description: 'Tu cuenta se ha creado correctamente.',
                })
                router.push('login')
            } else {
                toast({
                    title: 'Error',
                    description: response.data.message || 'Error al registrar',
                    variant: 'destructive',
                })
            }
        })
        .catch(error => {
            // console.log(error);
            toast({
                title: 'Error',
                description: error.response.data.message || 'No se pudo crear la cuenta. Inténtalo de nuevo.',
                variant: 'destructive',
            })
        })
        .finally(() => {
            isLoading.value = false
        })
}
)

</script>

<template>
    <Card class="mx-auto max-w-sm w-4/5">
        <CardHeader>
            <CardTitle class="text-2xl">
                Create una cuenta
            </CardTitle>
            <CardDescription>
                Registrate para acceder a DrinKings!
            </CardDescription>
        </CardHeader>
        <CardContent>
            <form @submit="onSubmit" class="w-90 mx-auto space-y-4">
                <!-- Campo de Email -->
                <FormField v-slot="{ componentField }" name="email" :validate-on-blur="!isFieldDirty">
                    <FormItem>
                        <FormLabel>Email</FormLabel>
                        <FormControl>
                            <Input type="email" placeholder="email@example.com" v-bind="componentField" />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                </FormField>

                <!-- Campo de Nombre de Usuario -->
                <FormField v-slot="{ componentField }" name="username" :validate-on-blur="!isFieldDirty">
                    <FormItem>
                        <FormLabel>Nombre de Usuario</FormLabel>
                        <FormControl>
                            <Input type="text" placeholder="ManoloGarcía" v-bind="componentField" />
                        </FormControl>
                        <FormDescription>Este será tu nombre público.</FormDescription>
                        <FormMessage />
                    </FormItem>
                </FormField>

                <!-- Campo de Contraseña -->
                <FormField v-slot="{ componentField }" name="password" :validate-on-blur="!isFieldDirty">
                    <FormItem>
                        <FormLabel>Contraseña</FormLabel>
                        <FormControl>
                            <Input type="password" placeholder="********" v-bind="componentField" />
                        </FormControl>
                        <FormMessage />
                    </FormItem>
                </FormField>

                <!-- Botón de envío -->
                <Button type="submit" class="w-full" :disabled="isLoading">
                    <LoaderCircle v-if="isLoading" class="w-5 h-5 mr-2" />
                    Crear Cuenta
                </Button>

                <!-- Redirección al inicio de sesión -->
                <div class="mt-4 text-center text-sm">
                    ¿Ya tienes cuenta?
                    <router-link class="underline" to="login" color="foreground">
                        Inicia sesión
                    </router-link>
                </div>
            </form>
        </CardContent>
    </Card>
</template>
