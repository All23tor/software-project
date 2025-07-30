<template>
  <div class="category-carousel-wrapper">
    <div class="category-carousel" :style="carouselStyle">
      <div
          class="category"
          v-for="(cat, i) in repeatedCategories"
          :key="i"
          :class="{ active: cat.name === selected.name }"
          @click="$emit('select', cat)"
      >
        <span class="icon">{{ cat.icon }}</span>
        <span class="name">{{ cat.name }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import {categories} from "@/data/categories.js";
defineProps(['categories', 'selected'])
const emit = defineEmits(['select'])

const repeatedCategories = computed(() => [...categories, ...categories])
const animationDuration = computed(() => `${categories.length * 3}s`)
const carouselStyle = computed(() => ({
  animation: `scroll-left ${animationDuration.value} linear infinite`
}))
</script>

<style scoped>
.category-carousel-wrapper {
  overflow: hidden;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
}

.category-carousel {
  display: inline-flex;
  gap: 1rem;
  white-space: nowrap;
}

.category {
  flex: 0 0 auto;
  background: #ffeaea;
  padding: 0.75rem 1.5rem;
  border-radius: 999px;
  font-weight: 600;
  font-size: 1.2rem;
  color: #e05656;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.2s ease;
  white-space: nowrap;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.6rem;
}

.category .icon {
  font-size: 1.5rem;
}

.category:hover {
  background: #e05656;
  color: white;
  transform: translateY(-2px);
}

.category.active {
  background: #e05656 !important;
  color: white !important;
  transform: scale(1.05);
}

@keyframes scroll-left {
  from {
    transform: translateX(0%);
  }
  to {
    transform: translateX(-50%);
  }
}
</style>