package PME.myfitnessbuddy.storage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import PME.myfitnessbuddy.model.Person;

import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    long insertPerson(Person person);

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

    @Query("SELECT * from Person")
    List<Person> getPersons();

    @Query("SELECT * from Person ORDER BY nickname ASC")
    List<Person> getPersonSortedByNickname();

    @Query("SELECT * from Person ORDER BY id DESC LIMIT 1")
    Person getLastEntry();

    @Query("SELECT * FROM Person WHERE nickname LIKE :search")
    List<Person> getPersonForNickname(String search);
}
