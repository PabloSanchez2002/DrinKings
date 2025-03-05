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
    joinLeague: (values: any) => void;
    isLoading: boolean;
}>();

// Validación del formulario
const joinLeagueSchema = toTypedSchema(
    z.object({
        shareToken: z.string().min(1, { message: 'El código de la liga es requerido' }),
    })
)


const { handleSubmit, isFieldDirty } = useForm({
    validationSchema: joinLeagueSchema,
})


const onSubmit = handleSubmit(async (createLeagueSchema) => {
    props.joinLeague(createLeagueSchema);

});
</script>

<template>
    <form @submit="onSubmit" class="w-90 mx-auto space-y-3">
        <!-- Share Token Field -->
        <FormField v-slot="{ componentField }" name="shareToken" :validate-on-blur="!isFieldDirty">
            <div class="grid grid-cols-4 items-center gap-4">
                <Label for="shareToken" class="text-right">Código</Label>
                <FormControl>
                    <Input id="shareToken" placeholder="Código de la liga" class="col-span-3" v-bind="componentField" />
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
                Únete a la liga!
            </Button>
        </DialogFooter>
    </form>
</template>
