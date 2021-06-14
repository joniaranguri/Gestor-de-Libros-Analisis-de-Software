package dtos;

public class Usuario implements Comparable<Usuario> {
    private String username;
    private String password;


    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object usuario) {
        return this == usuario || (usuario instanceof Usuario && username.equals(((Usuario) usuario).username));
    }

    @Override
    public int compareTo(Usuario usuario) {
        return username.compareTo(usuario.username);
    }

    @Override
    public String toString() {
        return
                "Usuario               : " + username;
    }


    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
