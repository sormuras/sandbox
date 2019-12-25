import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lions24 {
  public static void main(String[] args) throws Exception {
    // java src/Lions24.java sync ${YEAR}
    if (args.length == 2 && args[0].equals("sync")) {
      int year = Integer.parseInt(args[1]);
      for (var day = 1; day <= 24; day++) {
        var url = new URL(String.format("http://lions24.de/request.php?d=%d-12-%02d", year, day));
        try (var reader = new BufferedReader(new InputStreamReader(url.openStream(), "Cp1252"))) {
          var path = Path.of(String.format("%1$d/%1$d-12-%2$02d.html", year, day));
          var lines = reader.lines().collect(Collectors.toList());
          Files.write(path, lines);
          Thread.sleep(123);
        }
      }
      return;
    }
    // java src/Lions24.java list ${YEAR}
    if (args.length == 2 && args[0].equals("list")) {
      var numbers = new TreeMap<>();
      var doors = Door.allOf(Integer.parseInt(args[1]));
      for (var door : doors) {
        for (var item : door.items) {
          for (var number : item.numbers) {
            numbers.put(number, item);
          }
        }
      }
      numbers.forEach((key, value) -> System.out.printf("%5s -> %s%n", key, value));
      return;
    }
    System.out.println("?");
  }

  static class Item {
    static List<Item> allOf(String input) {
      // System.out.println(input);
      var items = new ArrayList<Item>();
      var pattern = Pattern.compile("\\Q<h2>\\E(.+?)\\Q</h2><h3>\\E(.+?)\\Q</h3><ul>\\E(.+?)\\Q</ul>\\E");
      var matcher = pattern.matcher(input);
      var numbers = Pattern.compile("\\Q<li>\\E(.+?)\\Q</li>\\E");
      while (matcher.find()) {
        var item = new Item();
        items.add(item);
        item.caption = matcher.group(1);
        item.donator = matcher.group(2);
        var numberMatcher = numbers.matcher(matcher.group(3));
        while (numberMatcher.find()) {
          item.numbers.add(Integer.parseInt(numberMatcher.group(1)));
        }
      }
      return items;
    }

    public String caption = ""; // "Geschenk-Gutschein im Wert von 50,00 €";
    public String donator = ""; // "Gespendet von: Modehaus Testhaus"
    public Set<Integer> numbers = new TreeSet<>();

    @Override
    public String toString() {
      // return "Item [caption=" + caption + ", donator=" + donator + ", numbers=" + numbers + "]";
      return caption + " - " + donator;
    }
  }

  static class Door {

    static List<Door> allOf(int year) throws Exception {
      var doors = new ArrayList<Door>();
      for(var day = 1; day <= 24; day++) {
        var path = Path.of(String.format("%1$d/%1$d-12-%2$02d.html", year, day));
        if (Files.isRegularFile(path)) {
          var door = new Door();
          doors.add(door);
          door.id = String.format("%d-%02d", year, day);
          door.day = day;
          door.year = year;
          door.items = Item.allOf(Files.readString(path));
          if (door.items.isEmpty()) throw new Error("No items for " + door.id + "?!");
        }
      }
      return doors;
    }


    public String bodyhtml = "";
    public String id; // "2013-09", that is year + "-" + day of december
    public int day = -1; // 9
    public int year = -1; // 2013
    public List<Item> items = new ArrayList<>();

    public boolean contains(Integer number) {
      for (Item item : items) {
        if (item.numbers.contains(number)) {
          return true;
        }
      }
      return false;
    }

    @Override
    public String toString() {
      return id + " " + items;
    }
  }
}
