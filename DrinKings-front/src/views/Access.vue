<script setup lang="ts">

</script>

<template>
    <!-- Image, Title, and Text Section -->
    <div class="py-4 text-center">
        <div class="pb-4 pt-4">
            <img src="/petNoBG.png" alt="DrinKings Logo" class="mx-auto h-40 w-40 object-cover rounded-lg" />
        </div>
        <h1 class="text-2xl font-bold mb-2">🍺 ¡Bienvenido a DrinKings! 👑</h1>
        <p class="text-gray-600 px-3">
            Donde los reyes de la bebida compiten por la corona
        </p>
    </div>
    <div className="content__container">
        <div className="main__container">
            <router-view />
        </div>
    </div>
</template>
