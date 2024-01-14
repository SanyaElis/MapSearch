package ru.cs.vsu.eliseev.OSMReader.model;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class Relation extends ElementOnMap {

    private final List<Member> members;

    public Relation(long id) {
        super(id);
        this.members = new ArrayList<>();
    }

    @Override
    public String getId() {
        return "R" + id;
    }

    public List<Member> getMembers() {
        return List.copyOf(members);
    }

    public void addMember(String role, ElementOnMap element) {
        if (element == null) {
            throw new NullPointerException("Element can't be null");
        }

        members.add(new Member(role, element));
    }

    public void removeMember(ElementOnMap e) {
        members.removeIf(member -> e == member.elementOnMap);
    }

    public String getMemberRole(ElementOnMap e) {
        for (Member member : members) {
            if (member.elementOnMap == e)
                return member.role;
        }
        throw new RuntimeException("Element " + e.getId() + " not found");
    }


    private class Member {
        private final String role;
        private final ElementOnMap elementOnMap;

        public Member(String role, ElementOnMap elementOnMap) {
            this.role = role;
            this.elementOnMap = elementOnMap;
        }
    }
}
