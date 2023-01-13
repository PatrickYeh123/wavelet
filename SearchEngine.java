import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> history = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Try: /add?s=anewstringtoadd or /search?s=app", num);
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                history.add(parameters[1]);
                return String.format("Added %s to list!", parameters[1]);
            }
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String search = parameters[1];
                    StringBuilder str = new StringBuilder();
                    for (String x : history) {
                        if (x.contains(search)) {
                            str.append(x);
                            str.append("\n");
                        }
                    }
                    return String.format("Results:\n%s", str.toString());
                } else {
                    return "404 Not Found!";
                }
            }
            return "404 Not Found!";
        }

        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
