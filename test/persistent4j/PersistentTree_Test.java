package persistent4j;

import persistent4j.PersistentTree;

public final class PersistentTree_Test {

    public static void main(String args[]) {

        // checking if assertions are enabled
        boolean assertions_enabled = false;
        assert assertions_enabled = true;
        if (assertions_enabled) {

            System.out.println("Running PersistentTreeTest tests...");

            //-------------------- isEmpty() -----------------------------------//

            // empty tree
            assert new PersistentTree().isEmpty() == true;

            // non-empty tree
            assert new PersistentTree().add(0).isEmpty() == false;

            // after deleting all the nodes
            assert new PersistentTree().add(0).del(0).isEmpty() == true;

            // test of presistency preserving
            PersistentTree t_isEmpty_1 = new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(15);
                    t_isEmpty_1.isEmpty();
                    assert t_isEmpty_1.toString().equals("(((2) 4) 5 ((6) 7 (15)))");


            //-------------------- contains() ----------------------------------//

            // empty tree
            assert new PersistentTree().contains(0) == false;

            // non-empty tree
            assert new PersistentTree().add(0).contains(0) == true;
            assert new PersistentTree().add(10).add(5).add(15).add(8).contains(8) == true;

            // nonexistent value
            assert new PersistentTree().add(10).add(5).add(15).add(8).contains(20) == false;

            // test of presistency preserving
            PersistentTree t_contains_1 = new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(16);
                    t_contains_1.contains(16);
                    t_contains_1.contains(100);
                    assert t_contains_1.toString().equals("(((2) 4) 5 ((6) 7 (16)))");


            //-------------------- get() ----------------------------------//

            // empty tree
            assert new PersistentTree().get(0) == null;

            // non-empty tree
            assert (Integer) new PersistentTree().add(0).get(0) == 0;
            assert (Integer) new PersistentTree().add(10).add(5).add(15).add(8).get(8) == 8;

            // nonexistent value
            assert new PersistentTree().add(10).add(5).add(15).add(8).get(20) == null;

            // test of presistency preserving
            PersistentTree t_get_1 = new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(16);
                    t_get_1.get(16);
                    t_get_1.get(100);
                    assert t_get_1.toString().equals("(((2) 4) 5 ((6) 7 (16)))");


            //-------------------- add() ---------------------------------------//

            // adding elements
            assert new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(12).add(10).add(8).add(11).add(3).add(17).add(13)
                    .toString().equals("(((2 (3)) 4) 5 ((6) 7 (((8) 10 (11)) 12 ((13) 17))))");

            // adding element already present in the tree
            assert new PersistentTree().add(5).add(6).add(6).add(5).toString().equals("(5 (6))");

            // test of presistency preserving
            PersistentTree t_add_1 = new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(17);
                    t_add_1.add(20).add(3).add(2).add(3).add(5).add(8);
                    assert t_add_1.toString().equals("(((2) 4) 5 ((6) 7 (17)))");


            //-------------------- del() ---------------------------------------//

            // deleting leaves
            assert new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(12).del(2).del(12)
                    .toString().equals("((4) 5 ((6) 7))");

            // deleting nodes with one child
            assert new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(12).add(17).del(2).del(17)
                    .toString().equals("((4) 5 ((6) 7 (12)))");

            // deleting nodes with two children
            assert new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(12).add(10).add(8).add(11).add(3).add(17).add(13).del(7)
                    .toString().equals("(((2 (3)) 4) 5 ((6) 8 ((10 (11)) 12 ((13) 17))))");
            assert new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(12).add(10).add(8).add(11).add(3).add(17).add(13).del(10)
                    .toString().equals("(((2 (3)) 4) 5 ((6) 7 (((8) 11) 12 ((13) 17))))");

            // deleting the root with one child
            assert new PersistentTree().add(5).add(7).add(6).add(9).del(5).toString().equals("((6) 7 (9))");
            assert new PersistentTree().add(5).add(3).add(1).del(5).toString().equals("((1) 3)");

            // deleting the root with two children
            assert new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(12).add(10).add(8).add(11).add(3).add(17).add(13).del(5)
                    .toString().equals("(((2 (3)) 4) 6 (7 (((8) 10 (11)) 12 ((13) 17))))");
            assert new PersistentTree().add(5).add(3).add(20).add(10).add(16).add(12).add(17).del(5)
                    .toString().equals("((3) 10 (((12) 16 (17)) 20))");

            // deleting nonexistent element
            assert new PersistentTree().add(5).add(4).add(7).del(10).del(10)
                    .toString().equals("((4) 5 (7))");

            // deleting from empty tree
            assert new PersistentTree().del(10).toString().equals("()");

            // deleting all the elements
            assert new PersistentTree().add(5).add(6).add(12).add(10).del(5).del(10).del(12).del(5).del(6)
                    .toString().equals("()");

            // test of presistency preserving
            PersistentTree t_del_1 = new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(18);
                    t_del_1.del(20).del(5).del(4).del(12).del(2).del(6).del(7).del(8);
                    assert t_del_1.toString().equals("(((2) 4) 5 ((6) 7 (18)))");


            //-------------------- set() ---------------------------------------//

            // updating leaves
            assert new PersistentTree().add(50).add(40).add(70).set(40,44).set(70,77)
                    .toString().equals("((44) 50 (77))");

            // updating intermediate nodes
            assert new PersistentTree().add(50).add(40).add(70).add(30).add(80).set(40,44).set(70,77)
                    .toString().equals("(((30) 44) 50 (77 (80)))");

            // updating the root
            assert new PersistentTree().add(50).add(40).add(70).add(30).add(80).set(50,55)
                    .toString().equals("(((30) 40) 55 (70 (80)))");

            // updating nonexistent element
            assert new PersistentTree().add(50).add(40).add(70).set(400,44).set(700,77)
                    .toString().equals("((40) 50 (70))");

            // test of presistency preserving
            PersistentTree t_set_1 = new PersistentTree().add(5).add(4).add(8).add(7).add(2).add(19);
                    t_set_1.set(4,3).set(19,25).set(5,6).set(8,10);
                    assert t_set_1.toString().equals("(((2) 4) 5 ((7) 8 (19)))");


            //-------------------- size() --------------------------------------//

            // empty tree
            assert new PersistentTree().size() == 0;

            // non-empty tree
            assert new PersistentTree().add(5).add(4).add(7).add(6).add(2).add(12).add(10).add(8).add(11).add(3).add(17).add(13)
                    .size() == 12;

            // deleting some nodes
            assert new PersistentTree().add(5).add(6).add(12).add(10).del(5).del(10)
                    .size() == 2;

            // deleting nonexisting node
            assert new PersistentTree().add(5).add(6).add(12).add(10).add(3).del(99)
                    .size() == 5;

            // deleting all the nodes
            assert new PersistentTree().add(5).add(6).add(12).add(10).del(5).del(10).del(12).del(6)
                    .size() == 0;

            // updating nodes
            assert new PersistentTree().add(5).add(7).add(12).add(10).add(3).set(5,6).set(12,99)
                    .size() == 5;


            //-------------------- equals() ------------------------------------//

            // empty tree
            assert new PersistentTree().equals(new PersistentTree());

            // after add() called one time
            assert new PersistentTree().add(5).equals(new PersistentTree().add(5));

            // after add() called more times
            assert new PersistentTree().add(5).add(7).add(12).equals(new PersistentTree().add(5).add(7).add(12));

            // after del() at the beginning
            assert new PersistentTree().add(5).add(7).add(12).del(5).equals(new PersistentTree().add(5).add(7).add(12).del(5));

            // after del() in the middle
            assert new PersistentTree().add(5).add(7).add(12).del(7).equals(new PersistentTree().add(5).add(7).add(12).del(7));

            // after del() at the end
            assert new PersistentTree().add(5).add(7).add(12).del(12).equals(new PersistentTree().add(5).add(7).add(12).del(12));

            // after set() at the beginning
            assert new PersistentTree().add(5).add(7).add(12).set(5,99).equals(new PersistentTree().add(5).add(7).add(12).set(5,99));

            // after set() in the middle
            assert new PersistentTree().add(5).add(7).add(12).set(7,99).equals(new PersistentTree().add(5).add(7).add(12).set(7,99));

            // after set() at the end
            assert new PersistentTree().add(5).add(7).add(12).set(12,99).equals(new PersistentTree().add(5).add(7).add(12).set(12,99));


            //-------------------- hashCode() ----------------------------------//

            // empty tree
            assert new PersistentTree().hashCode() == new PersistentTree().hashCode();

            // after add() called one time
            assert new PersistentTree().add(5).hashCode() == new PersistentTree().add(5).hashCode();

            // after add() called more times
            assert new PersistentTree().add(5).add(7).add(12).hashCode() == new PersistentTree().add(5).add(7).add(12).hashCode();

            // after del() at the beginning
            assert new PersistentTree().add(5).add(7).add(12).del(5).hashCode() == new PersistentTree().add(5).add(7).add(12).del(5).hashCode();

            // after del() in the middle
            assert new PersistentTree().add(5).add(7).add(12).del(7).hashCode() == new PersistentTree().add(5).add(7).add(12).del(7).hashCode();

            // after del() at the end
            assert new PersistentTree().add(5).add(7).add(12).del(12).hashCode() == new PersistentTree().add(5).add(7).add(12).del(12).hashCode();

            // after set() at the beginning
            assert new PersistentTree().add(5).add(7).add(12).set(5,99).hashCode() == new PersistentTree().add(5).add(7).add(12).set(5,99).hashCode();

            // after set() in the middle
            assert new PersistentTree().add(5).add(7).add(12).set(7,99).hashCode() == new PersistentTree().add(5).add(7).add(12).set(7,99).hashCode();

            // after set() at the end
            assert new PersistentTree().add(5).add(7).add(12).set(12,99).hashCode() == new PersistentTree().add(5).add(7).add(12).set(12,99).hashCode();



            //-------------------- genericity------------- ---------------------//

            //String s = new PersistentTree<String>().add("s").get("s");


            //-------------------- supertype compatibility ---------------------//

            // equals & interface
            IPersistentTree t_iface_1 = new PersistentTree().add(10);
            assert t_iface_1.equals(new PersistentTree().add(10));

        }

    }
}
