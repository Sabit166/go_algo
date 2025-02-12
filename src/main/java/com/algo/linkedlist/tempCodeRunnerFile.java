    void setPointers() {
        map.clear();
        for (int i = 0; i < nodes.size() - 1; i++) {
            map.put(nodes.get(i).getNextPointOut(), nodes.get(i + 1).getNextPointIn());
        }
    }