<script setup lang="ts">
import { Card, CardHeader, CardTitle, CardDescription, CardContent, CardFooter } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import apiClient from '@/services/apiClient';
import { h, onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useToast } from '@/components/ui/toast/use-toast'


interface Score {
    id: number;
    score: number;
    date: string;
}

interface League {
    id: number;
    name: string;
    description: string;
    shareToken: string;
    scores: Score[];
}

// Reactive data
const league = ref<League | null>(null);
const token = localStorage.getItem('auth_token');
const route = useRoute();
const { toast } = useToast();

// Counter data for each button
const counters = ref<number[]>([0, 0, 0]);
const points = [10, 7, 3]; // Points associated with each button

onMounted(() => {
    const leagueId = Number(route.params.id);
    if (leagueId) loadLeagueData(leagueId);
});

const loadLeagueData = async (id: number) => {
    apiClient.get(`/league/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
        .then(response => {
            league.value = response.data;
            console.log('League fetched successfully:', league.value);
        })
        .catch(error => {
            console.error('Error fetching league:', error);
        });
};

// Watch for changes in the route parameter
watch(() => route.params.id, (newLeagueId) => {
    if (newLeagueId) loadLeagueData(Number(newLeagueId));
});

// Calculate the total score
const calculateTotal = () => {
    return counters.value.reduce((total, count, index) => total + count * points[index], 0);
};
const saveScores = () => {
    const score = calculateTotal();
    const leagueId = league.value?.id;
    const date = new Date().toISOString();

    const requestBody = {
        leagueId: leagueId,
        score: score,
        date: date // Using "tomorrow" as a string explicitly
    };

    console.log('Saving scores:', requestBody);

    apiClient.post(`/score`, requestBody, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
        .then(response => {
            // console.log('Scores saved successfully:', response.data);
            toast({
                title: 'Puntuación guardada!',
                // description: 'Liga ' + response.data.name + ' creada correctamente.',
                duration: 1000,
            })
            counters.value = [0, 0, 0];
        })
        .catch(error => {
            console.error('Error saving scores:', error);
            toast({
                title: 'Error',
                description: error.response.data || 'Error al guardar la puntuación',
                variant: 'destructive',
            })
        });
};

</script>

<template>
    <main class="flex min-h-[calc(100vh_-_theme(spacing.16))] flex-1 flex-col gap-4 bg-muted/40 p-4 md:gap-8 md:p-10">
        <div class="mx-auto grid w-full max-w-6xl gap-2">
            <h1 class="text-3xl font-semibold">
                {{ league?.name }}
            </h1>
            <p class="text-lg">
                {{ league?.description }}
            </p>
        </div>
        <div class="mx-auto grid w-full max-w-6xl items-start gap-6 md:grid-cols-[180px_1fr] lg:grid-cols-[250px_1fr]">
            <div class="grid gap-6">
                <Card>
                    <CardHeader>
                        <CardTitle>Añade bebidas</CardTitle>
                        <CardDescription>
                            Marca aquí tus consumiciones.
                        </CardDescription>
                    </CardHeader>
                    <CardContent class="flex flex-col gap-4">
                        <!-- Fila de imágenes -->
                        <div class="flex justify-between gap-2 my-4">
                            <div class="text-center">
                                <!-- Case 1: Cubata -->
                                <Button variant="secondary" class="p-0 w-24 h-24 rounded-lg" @click="counters[0]++">
                                    <img src="/items/cubata.jpg" alt="Cubata" class="rounded-lg w-20" />
                                </Button>
                                <p class="font-semibold mt-2">Cubata {{ points[0] }} puntos</p>
                            </div>

                            <div class="text-center">
                                <!-- Case 2: Tercio -->
                                <Button variant="secondary" class="p-0 w-24 h-24 rounded-lg" @click="counters[1]++">
                                    <img src="/items/tercio.png" alt="Tercio" class="rounded-lg h-24" />
                                </Button>
                                <p class="font-semibold mt-2">Tercio {{ points[1] }} puntos</p>
                            </div>

                            <div class="text-center">
                                <!-- Case 3: Another Tercio -->
                                <Button variant="secondary" class="p-0 w-24 h-24 rounded-lg" @click="counters[2]++">
                                    <img src="/items/tercio.png" alt="Another Tercio" class="rounded-lg h-20" />
                                </Button>
                                <p class="font-semibold mt-2"> Botellín {{ points[2] }} puntos</p>
                            </div>
                        </div>
                        <!-- Fila de botones y contadores -->
                        <div class="flex justify-between gap-2">
                            <div v-for="(counter, index) in counters" :key="'control-' + index"
                                class="flex flex-col items-center">
                                <div class="flex items-center gap-2">
                                    <Button variant="outline" size="icon" class="px-1 py-1" @click="counters[index]++">
                                        +
                                    </Button>
                                    <span class="text-lg font-bold">{{ counter }}</span>
                                    <Button variant="outline" size="icon" class="px-2 py-1" :disabled="counter === 0"
                                        @click="counters[index]--">
                                        -
                                    </Button>
                                </div>
                            </div>
                        </div>
                    </CardContent>
                    <CardFooter class="border-t px-6 py-4 flex justify-between">
                        <Button :variant="calculateTotal() === 0 ? 'secondary' : 'default'" @click="saveScores()"
                            :disabled="calculateTotal() === 0">
                            Guardar
                        </Button>
                        <span class="text-lg font-bold">Total: {{ calculateTotal() }} puntos</span>
                    </CardFooter>
                </Card>
            </div>
        </div>
    </main>
</template>
