<script setup lang="ts">
import { Button } from '@/components/ui/button'
import { FormControl, FormField, FormMessage } from '@/components/ui/form'
import { DialogFooter, DialogClose } from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { LoaderCircle } from 'lucide-vue-next'
import { toTypedSchema } from '@vee-validate/zod'

import { useForm } from 'vee-validate'
import * as z from 'zod';

const props = defineProps<{
    createLeague: (values: any) => void;
    isLoading: boolean;
}>();

// Validación del formulario
const createLeagueSchema = toTypedSchema(
    z.object({
        name: z.string().min(3, { message: 'El nombre debe de ser de 3 o mas caracteres' }),
        description: z.string().min(3, { message: 'La descripción es requerida' }),
    })
)


const { handleSubmit, isFieldDirty } = useForm({
    validationSchema: createLeagueSchema,
});


const onSubmit = handleSubmit(async (createLeagueSchema) => {
    props.createLeague(createLeagueSchema);

});
</script>

<template>
    <form @submit="onSubmit" class="w-90 mx-auto space-y-3">
        <!-- Name Field -->
        <FormField v-slot="{ componentField }" name="name" :validate-on-blur="!isFieldDirty">
            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="name" class="text-right">Nombre</Label>
                <FormControl>
                    <Input id="name" placeholder="Nombre de la liga" class="col-span-3" v-bind="componentField" />
                </FormControl>
                <FormMessage class="col-span-4 text-sm text-red-500" />
            </div>
        </FormField>
        <!-- Description Field -->
        <FormField v-slot="{ componentField }" name="description" :validate-on-blur="!isFieldDirty">
            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="description" class="text-right">Descripción</Label>
                <FormControl>
                    <Input id="description" placeholder="Descripción" class="col-span-3" v-bind="componentField" />
                </FormControl>
                <FormMessage class="col-span-4 text-sm text-red-500" />
            </div>
        </FormField>
        <!-- Form Footer -->
        <DialogFooter class="gap-4">
            <DialogClose as-child>
                <Button variant="outline">Cancel</Button>
            </DialogClose>
            <Button type="submit" :disabled="isLoading">
                <LoaderCircle v-if="isLoading" class="w-5 h-5 mr-2" />
                Crear una liga!
            </Button>
        </DialogFooter>
    </form>
</template>
