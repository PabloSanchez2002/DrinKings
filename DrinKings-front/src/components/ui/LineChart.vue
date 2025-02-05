<script setup lang="ts">
import { Line } from "vue-chartjs";
import {
    Chart as ChartJS,
    Title,
    Tooltip,
    Legend,
    LineElement,
    CategoryScale,
    LinearScale,
    PointElement,
    type ChartData,
    type ChartOptions,
} from "chart.js";

// Register Chart.js components
ChartJS.register(Title, Tooltip, Legend, LineElement, CategoryScale, LinearScale, PointElement);

interface Props {
    chartData: ChartData<"line">;
    chartOptions?: ChartOptions<"line">;
}

const props = defineProps<Props>();
</script>

<template>
    <n-card title="User Progress">
        <Line :data="props.chartData" :options="props.chartOptions" />
    </n-card>
</template>
