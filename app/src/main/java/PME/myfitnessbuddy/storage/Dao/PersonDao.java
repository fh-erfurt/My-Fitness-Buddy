package PME.myfitnessbuddy.storage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import PME.myfitnessbuddy.model.Person;

import java.util.List;

/**
 * Defines all sql operations needed to manage the person
 * */

@Dao
public interface PersonDao {

    @Insert
    List<Long> insert(Person... persons);

    @Update
    void update(Person... persons);

    @Delete
    void delete(Person... persons);

    @Query("DELETE FROM Person")
    void deleteAll();

    @Query("SELECT count(*) FROM Person")
    int count();

    @Query("SELECT * from Person ORDER BY id DESC LIMIT 1")
    List <Person> getLastEntry();

    @Query("SELECT * FROM Person WHERE nickname LIKE :search")
    List<Person> getPersonForNickname(String search);
}
