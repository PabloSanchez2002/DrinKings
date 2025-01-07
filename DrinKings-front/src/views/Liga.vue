<script>
import { Card, CardHeader, CardTitle, CardDescription, CardContent, CardFooter } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import apiClient from '@/services/apiClient';
import { ref } from 'vue';


export default {
    mounted() {
        // Access the leagueId from the route parameters
        const leagueId = this.$route.params.id;
        console.log('League ID:', leagueId);
    }
}

const leagueId = ref(1);
const getLeague = async (id) => {
    apiClient.get(`/league/${id}`)
        .then(response => {
            console.log('League fetched successfully:', response.data);
        })
        .catch(error => {
            console.error('Error fetching league:', error);
        });
}

getLeague(leagueId.value);

</script>

<template>
    <main class="flex min-h-[calc(100vh_-_theme(spacing.16))] flex-1 flex-col gap-4 bg-muted/40 p-4 md:gap-8 md:p-10">
        <div class="mx-auto grid w-full max-w-6xl gap-2">
            <h1 class="text-3xl font-semibold">
                Liga:
            </h1>
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
                        <div class="flex justify-between gap-2 my-4">
                            <Button variant="secondary" class="p-0 w-24 h-24 rounded-lg">
                                <img src="/items/cubata.jpg" alt="Cubata" class="rounded-lg w-20 " />
                            </Button>
                            <Button variant="secondary" class="p-0 w-24 h-24 rounded-lg">
                                <img src="/items/tercio.png" alt="Tercio" class="rounded-lg h-24" />
                            </Button>
                            <Button variant="secondary" class="p-0 w-24 h-24 rounded-lg">
                                <img src="/items/tercio.png" alt="Cubata" class="rounded-lg h-20" />
                            </Button>
                        </div>
                    </CardContent>
                    <CardFooter class="border-t px-6 py-4">
                        <Button>Save</Button>
                    </CardFooter>
                </Card>
            </div>
        </div>
    </main>
</template>
