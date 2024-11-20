<script setup>
import TodayQuest from './components/TodayQuest.vue';
import CompletedQuest from './components/CompletedQuest.vue';
</script>
<template>
    <div class="py-4 container-fluid">
        <div class="row">
            <div class="col-12">
                <TodayQuest />
            </div>
        </div>
        <div class="mt-4 row">
            <div class="col-12">
                <CompletedQuest />
            </div>
        </div>
    </div>
</template>
