package com.epam.task5.entity;

import com.epam.task5.util.IdGenerator;

public class Pier {
        private Long id;

        public Pier(long id){
            this.id = IdGenerator.generatePierId();
        }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pier pier = (Pier) o;

        return id.equals(pier.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pier{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
