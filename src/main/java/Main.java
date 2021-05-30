import com.google.gson.Gson;
import lombok.SneakyThrows;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Main {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @SneakyThrows
    public static void main(String[] args) {
        RetrofitClient client = RetrofitConfig.createClient(BASE_URL,
                GsonConverterFactory.create(), RetrofitClient.class);
        List <User> listUsers = infoUser(client);
        addUser(listUsers, client, newUser());
        editUser(listUsers, client, 2, newUser());
        deleteUser(listUsers, client, 2);
        findByID(client, 4);
        findByName(client, "Antonette");
        lastPostComments(client, 8);
        openToDo(client, 2);
    }

    public static List<User> infoUser(RetrofitClient client){
        System.out.println("~ ~ ~ ~ ~ ~ ~");
        System.out.println("Получаем информацию обо всех пользователях:");
        List <User> listUsers = RetrofitConfig.execute(client.getUser());
        for (User user : listUsers) System.out.println(user.getId() + " "+ user.getName());
        return listUsers;
    }

    public static List<User> addUser(List <User> listUsers, RetrofitClient client, User user){
        System.out.println("~ ~ ~ ~ ~ ~ ~");
        User newUser = RetrofitConfig.execute(client.addObject(user));
        System.out.println("Создаем новый объект:\n" + newUser);
        listUsers.add(newUser);
        System.out.println("Список пользователей:");
        for (User adduser : listUsers) System.out.println(adduser.getId() + " "+ adduser.getName());
        write("users.json", listUsers);
        return listUsers;
    }

    public static List <User> editUser(List <User> listUsers, RetrofitClient client, Integer updateUserID, User user){
        System.out.println("~ ~ ~ ~ ~ ~ ~");
        User updateUser = RetrofitConfig.execute(client.updateUser(user, updateUserID));
        System.out.println("Обновляем " + updateUserID + "-й объект:");
        System.out.println(updateUser);
        listUsers.set(updateUserID-1, updateUser);
        System.out.println("Список пользователей:");
        for (User edituser : listUsers) System.out.println(edituser.getId() + " "+ edituser.getName());
        write("users.json", listUsers);
        return listUsers;
    }

    public static List <User> deleteUser(List <User> listUsers, RetrofitClient client, Integer deleteUserID){
        System.out.println("~ ~ ~ ~ ~ ~ ~");
        User deleteUser = RetrofitConfig.execute(client.deleteUser(deleteUserID));
        System.out.println("Удаляем " + deleteUserID + "-й объект:");
        listUsers.set(deleteUserID-1, deleteUser);
        System.out.println("Список пользователей:");
        for (User deleteuser : listUsers) System.out.println(deleteuser.getId() + " "+ deleteuser.getName());
        return listUsers;
    }

    public static List<User> findByID(RetrofitClient client, Integer findUserID){
        System.out.println("~ ~ ~ ~ ~ ~ ~");
        List<User> findByID = RetrofitConfig.execute(client.getUserByID(findUserID));
        System.out.println(findUserID + "-й пользователь:\n" + findByID);
        return findByID;
    }

    public static List<User> findByName(RetrofitClient client,String findUserName){
        System.out.println("~ ~ ~ ~ ~ ~ ~");
        List<User> findByName = RetrofitConfig.execute(client.getUserByUserName(findUserName));
        System.out.println("Пользователь с именем " + findUserName + "\n" + findByName);
        return findByName;
    }

    public static List<Comment> lastPostComments(RetrofitClient client, Integer getUserID){
        System.out.println("~ ~ ~ ~ ~ ~ ~");
        List<Post> postsList = RetrofitConfig.execute(client.getUserPosts(getUserID));
        Post lastPost = Collections.max(postsList, Comparator.comparing(Post::getId));
        List<Comment> listComments = RetrofitConfig.execute(client.getComments(lastPost.getId()));
        String fileName = "user-" + getUserID + "-post-" + lastPost.getId() + "-comments.json";
        System.out.println("Комменты к последнему посту " + getUserID + "-го пользователя:\n" + listComments);
        write(fileName, listComments);
        return listComments;
    }

    public static List <ToDo> openToDo(RetrofitClient client, Integer toDoUserID){
        System.out.println("~ ~ ~ ~ ~ ~ ~");
        List<ToDo> listToDo = RetrofitConfig.execute(client.getTodo(toDoUserID.toString(), false));
        System.out.println("Открытые задачи для " + toDoUserID + "-го пользователя:\n" + listToDo);
        return listToDo;
    }

    @SneakyThrows
    public static <T> void write (String fileName, List <T> list){
        try (FileWriter fileJson = new FileWriter(fileName)){
            fileJson.write(new Gson().toJson(list));
            System.out.println("\nДанные записаны в файл " + fileName);
        }
    }

    private static User newUser() {
        return new User.UserBuilder()
                .name("Inna Khomenko")
                .username("itm.ikhomenko")
                .email("itm.ikhomenko@gmail.com")
                .address(new Address.AddressBuilder()
                    .street("23 veresnya")
                    .suite("13-4")
                    .city("Poltava")
                    .zipcode("36023")
                    .geo(new Geo.GeoBuilder()
                        .latitude(49.56F)
                        .longitude(34.50F)
                        .build())
                    .build())
                .phone("+380661796258")
                .website("https://github.com/InnaKhomenko82")
                .company(new Company.CompanyBuilder()
                    .nameCompany("GoIT")
                    .catchPhrase("Client Happiness Manager")
                    .bs("Java")
                    .build())
                .build();
    }
}