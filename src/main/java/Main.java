import com.google.gson.Gson;
import lombok.SneakyThrows;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.FileWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Main {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    public static Gson gson = new Gson();

    @SneakyThrows
    public static void main(String[] args) {
        RetrofitClient client = RetrofitConfig.createClient(BASE_URL,
                GsonConverterFactory.create(), RetrofitClient.class);

        System.out.println("~ ~ ~ ~ ~ ~ ~");
        System.out.println("Считываем данные в список объектов:");
        List <User> listUsers = RetrofitConfig.execute(client.getUser());
        //System.out.println(listUsers);
        for (User user : listUsers) System.out.println(user.getId() + " "+ user.getName());

        System.out.println("~ ~ ~ ~ ~ ~ ~");
        System.out.println("Добавляем новый объект:\n" + newUser());
        RetrofitConfig.execute(client.addObject(newUser()));
        listUsers.add(newUser());
        listUsers.add(newUser());
        System.out.println("++++");

        try (FileWriter fileJson = new FileWriter("users.json")){
            fileJson.write(new Gson().toJson(listUsers));
            System.out.println("Данные записаны в файл");
        }

        //RetrofitConfig.execute(client.addObject(newUser()));
        //System.out.println(listUsers);
        for (User user : listUsers) System.out.println(user.getId() + " "+ user.getName());

        //System.out.println("~ ~ ~ ~ ~ ~ ~");
        //System.out.println(RetrofitConfig.execute(client.updateUser(newUser(), 2)));

        //System.out.println("~ ~ ~ ~ ~ ~ ~");
        //System.out.println(RetrofitConfig.execute(client.deleteUser(8)));


        Integer userID = 8;
        List<Post> postsList = RetrofitConfig.execute(client.getUserPosts(userID));
        Post lastPost = Collections.max(postsList, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                if (o1.getId() < o2.getId())
                    return -1;
                if (o1.getId() == o2.getId())
                    return 0;
                return 1;
            }
        });
        List<Comment> listComments = RetrofitConfig.execute(client.getComments(lastPost.getId()));
        String fileName = "user-" + userID + "-post-" + lastPost.getId() + "-comments.json";
        try (FileWriter fileJson = new FileWriter(fileName)){
            fileJson.write(new Gson().toJson(listComments));
            System.out.println("Данные записаны в файл " + fileName);
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
