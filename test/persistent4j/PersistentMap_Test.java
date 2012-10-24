package persistent4j;

public final class PersistentMap_Test {

    public static void main(String args[]) {

        // checking if assertions are enabled
        boolean assertions_enabled = false;
        assert assertions_enabled = true;
        if (assertions_enabled) {

            System.out.println("Running PersistentMap tests...");


            //-------------------- isEmpty() -----------------------------------//

            // empty map
            assert new PersistentMap().isEmpty() == true;

            // non-empty map
            assert new PersistentMap().add(0,"A").isEmpty() == false;

            // after deleting all the elements
            assert new PersistentMap().add(0,"A").del(0).isEmpty() == true;

            // test of presistency preserving
            PersistentMap t_isEmpty_1 = new PersistentMap().add(5,"B").add(4,"C").add(7,"D").add(6,"E").add(2,"F").add(15,"G");
                    t_isEmpty_1.isEmpty();
                    assert t_isEmpty_1.toString().equals("{[2, F] [4, C] [5, B] [6, E] [7, D] [15, G]}");


            //-------------------- contains() ----------------------------------//

            // empty map
            assert new PersistentMap().contains(0) == false;

            // non-empty map
            assert new PersistentMap().add(0,"A").contains(0) == true;
            assert new PersistentMap().add(10,"A").add(5,"B").add(15,"C").add(8,"D").contains(8) == true;

            // nonexistent key
            assert new PersistentMap().add(10,"A").add(5,"B").add(15,"C").add(8,"D").contains(20) == false;

            // test of presistency preserving
            PersistentMap t_contains_1 = new PersistentMap().add(5,"A").add(4,"B").add(7,"C").add(6,"D").add(2,"E").add(16,"F");
                    t_contains_1.contains(16);
                    t_contains_1.contains(100);
                    assert t_contains_1.toString().equals("{[2, E] [4, B] [5, A] [6, D] [7, C] [16, F]}");


            //-------------------- get() ----------------------------------//

            // empty map
            assert new PersistentMap().get(0) == null;

            // non-empty map
            assert (String) new PersistentMap().add(0,"A").get(0) == "A";
            assert (String) new PersistentMap().add(10,"A").add(5,"B").add(15,"C").add(8,"D").get(8) == "D";

            // nonexistent key
            assert new PersistentMap().add(10,"A").add(5,"B").add(15,"C").add(8,"D").get(20) == null;

            // test of presistency preserving
            PersistentMap t_get_1 = new PersistentMap().add(5,"A").add(4,"B").add(7,"C").add(6,"D").add(2,"E").add(16,"F");
                    t_get_1.get(16);
                    t_get_1.get(100);
                    assert t_get_1.toString().equals("{[2, E] [4, B] [5, A] [6, D] [7, C] [16, F]}");


            //-------------------- add() ---------------------------------------//

            // adding elements
            assert new PersistentMap().add(5,"A").add(4,"B").add(7,"C").add(6,"D").add(2,"E")
                    .add(12,"F").add(10,"G").add(8,"H").add(11,"I").add(3,"J").add(17,"K").add(13,"L")
                    .toString().equals("{[2, E] [3, J] [4, B] [5, A] [6, D] [7, C] [8, H] [10, G] [11, I] [12, F] [13, L] [17, K]}");

            // adding element already present in the map
            assert new PersistentMap().add(5,"A").add(6,"B").add(6,"C").add(5,"D").toString().equals("{[5, A] [6, B]}");

            // test of presistency preserving
            PersistentMap t_add_1 = new PersistentMap().add(5,"A").add(4,"B").add(7,"C").add(6,"D").add(2,"E").add(17,"F");
                    t_add_1.add(20,"G").add(3,"H").add(2,"I").add(3,"J").add(5,"K").add(8,"L");
                    assert t_add_1.toString().equals("{[2, E] [4, B] [5, A] [6, D] [7, C] [17, F]}");


            //-------------------- del() ---------------------------------------//

            // deleting some elements
            assert new PersistentMap().add(5,"A").add(4,"B").add(7,"C").add(6,"D").add(2,"E").add(12,"F").del(2).del(12)
                    .toString().equals("{[4, B] [5, A] [6, D] [7, C]}");
            assert new PersistentMap().add(5,"A").add(4,"B").add(7,"C").add(6,"D").add(2,"E").add(12,"F").add(17,"G").del(2).del(17)
                    .toString().equals("{[4, B] [5, A] [6, D] [7, C] [12, F]}");

            // deleting nonexistent element
            assert new PersistentMap().add(5,"A").add(4,"B").add(7,"C").del(10).del(10)
                    .toString().equals("{[4, B] [5, A] [7, C]}");

            // deleting from empty map
            assert new PersistentMap().del(10).toString().equals("{}");

            // deleting all the elements
            assert new PersistentMap().add(5,"A").add(6,"B").add(12,"C").add(10,"D").del(5).del(10).del(12).del(5).del(6)
                    .toString().equals("{}");

            // test of presistency preserving
            PersistentMap t_del_1 = new PersistentMap().add(5,"A").add(4,"B").add(7,"C").add(6,"D").add(2,"E").add(18,"F");
                    t_del_1.del(20).del(5).del(4).del(12).del(2).del(6).del(7).del(8);
                    assert t_del_1.toString().equals("{[2, E] [4, B] [5, A] [6, D] [7, C] [18, F]}");


            //-------------------- set() ---------------------------------------//

            // updating some elements
            assert new PersistentMap().add(50,"A").add(40,"B").add(70,"C").set(40,"X").set(70,"Y")
                    .toString().equals("{[40, X] [50, A] [70, Y]}");
            assert new PersistentMap().add(50,"A").add(40,"B").add(70,"C").add(30,"D").add(80,"E").set(40,"X").set(70,"Y")
                    .toString().equals("{[30, D] [40, X] [50, A] [70, Y] [80, E]}");

            // updating nonexistent element
            assert new PersistentMap().add(50,"A").add(40,"B").add(70,"C").set(400,"X").set(700,"Y")
                    .toString().equals("{[40, B] [50, A] [70, C]}");

            // test of presistency preserving
            PersistentMap t_set_1 = new PersistentMap().add(5,"A").add(4,"B").add(8,"C").add(7,"D").add(2,"E").add(19,"F");
                    t_set_1.set(4,"W").set(19,"X").set(5,"Y").set(8,"Z");
                    assert t_set_1.toString().equals("{[2, E] [4, B] [5, A] [7, D] [8, C] [19, F]}");


            //-------------------- size() --------------------------------------//

            // empty map
            assert new PersistentMap().size() == 0;

            // non-empty map
            assert new PersistentMap().add(5,"A").add(4,"B").add(7,"C").add(6,"D").add(2,"E")
                    .size() == 5;

            // deleting some elements
            assert new PersistentMap().add(5,"A").add(6,"B").add(12,"C").add(10,"D").del(5).del(10)
                    .size() == 2;

            // deleting nonexisting element
            assert new PersistentMap().add(5,"A").add(6,"B").add(12,"C").add(10,"D").add(3,"E").del(99)
                    .size() == 5;

            // deleting all the elements
            assert new PersistentMap().add(5,"A").add(6,"B").add(12,"C").add(10,"D").del(5).del(10).del(12).del(6)
                    .size() == 0;

            // updating elements
            assert new PersistentMap().add(5,"A").add(6,"B").add(12,"C").add(10,"D").set(5,"X").set(12,"YY")
                    .size() == 4;


            //-------------------- equals() ------------------------------------//

            // empty map
            assert new PersistentMap().equals(new PersistentMap());

            // after add() called one time
            assert new PersistentMap().add(5,"A").equals(new PersistentMap().add(5,"A"));

            // after add() called more times
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").equals(new PersistentMap().add(5,"A").add(7,"B").add(12,"C"));

            // after del() at the beginning
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(5).equals(new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(5));

            // after del() in the middle
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(7).equals(new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(7));

            // after del() at the end
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(12).equals(new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(12));

            // after set() at the beginning
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(5,"X").equals(new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(5,"X"));

            // after set() in the middle
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(7,"X").equals(new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(7,"X"));

            // after set() at the end
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(12,"X").equals(new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(12,"X"));


            //-------------------- hashCode() ----------------------------------//

            // empty map
            assert new PersistentMap().hashCode() == new PersistentMap().hashCode();

            // after add() called one time
            assert new PersistentMap().add(5,"A").hashCode() == new PersistentMap().add(5,"A").hashCode();

            // after add() called more times
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").hashCode() == new PersistentMap().add(5,"A").add(7,"B").add(12,"C").hashCode();

            // after del() at the beginning
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(5).hashCode() == new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(5).hashCode();

            // after del() in the middle
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(7).hashCode() == new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(7).hashCode();

            // after del() at the end
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(12).hashCode() == new PersistentMap().add(5,"A").add(7,"B").add(12,"C").del(12).hashCode();

            // after set() at the beginning
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(5,"X").hashCode() == new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(5,"X").hashCode();

            // after set() in the middle
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(7,"X").hashCode() == new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(7,"X").hashCode();

            // after set() at the end
            assert new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(12,"X").hashCode() == new PersistentMap().add(5,"A").add(7,"B").add(12,"C").set(12,"X").hashCode();


            //-------------------- genericity-----------------------------------//

            // Integer & int
            int num_gen_1 = new PersistentMap<String,Integer>().add("A",8).add("B",6).set("B",12).del("A").get("B");
            assert num_gen_1 == 12;

            // String
            String str_gen_1 = new PersistentMap<String,String>().add("s","f").add("t","xx").set("t","zzz").del("s").get("t");
            assert str_gen_1 == "zzz";

            //-------------------- supertype compatibility ---------------------//

            // equals & interface
            IPersistentMap t_iface_1 = new PersistentMap().add(10,"A");
            assert t_iface_1.equals(new PersistentMap().add(10,"A"));

        }

    }
}
