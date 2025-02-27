<script setup lang="ts">
import { defineEmits } from 'vue';
import { useForm } from 'vee-validate';
import * as z from 'zod';


// Validación del formulario
const createLeagueSchema = z.object({
    name: z.string().min(3, 'El nombre debe de ser de 3 o más caracteres'),
    description: z.string().min(3, 'La descripción es requerida'),
});

const { handleSubmit } = useForm({
    validationSchema: createLeagueSchema,
});

// Emitir evento para enviar el formulario
const emit = defineEmits();
const onSubmit = handleSubmit(async (values) => {
    emit('submit', values); // Emitimos el evento con los valores del formulario
});
</script>

<template>
    <form @submit="onSubmit" class="space-y-3">
        <div class="grid grid-cols-4 gap-4">
            <Label for="name" class="text-right">Nombre</Label>
            <FormControl>
                <Input id="name" placeholder="Nombre de la liga" v-bind="field" />
            </FormControl>
        </div>
        <div class="grid grid-cols-4 gap-4">
            <Label for="description" class="text-right">Descripción</Label>
            <FormControl>
                <Input id="description" placeholder="Descripción de la liga" v-bind="field" />
            </FormControl>
        </div>
        <DialogFooter class="gap-4">
            <DialogClose as-child>
                <Button variant="outline">Cancelar</Button>
            </DialogClose>
            <Button type="submit">Crear una liga</Button>
        </DialogFooter>
    </form>
</template>
