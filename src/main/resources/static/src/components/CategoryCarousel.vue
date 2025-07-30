<template>
  <div class="carousel">
    <button @click="prev">⬅️</button>

    <div class="carousel-item">
      <span class="icon">{{ current.icon }}</span>
      <span class="name">{{ current.name }}</span>
    </div>

    <button @click="next">➡️</button>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { categories } from '../data/categories.js';

const index = ref(0);

const current = computed(() => categories[index.value]);

function next() {
  index.value = (index.value + 1) % categories.length;
}

function prev() {
  index.value = (index.value - 1 + categories.length) % categories.length;
}

// Exportamos el índice y categoría actual si otro componente (como MenuCard.vue) quiere usarlos
defineExpose({ current, index });
</script>

<style scoped>
.carousel {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 2rem;
  font-size: 2rem;
  margin-bottom: 2rem;
}

.carousel-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.name {
  font-weight: bold;
  font-size: 1.5rem;
}
</style>
