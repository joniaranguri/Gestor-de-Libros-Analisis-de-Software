package dtos;

public class Usuario  implements Comparable<Usuario>{
	  	private String Username;
	    private String Password;

	    
	    public Usuario(String username,String password) {
	    	this.Username = username;
	    	this.Password = password;
	    }
	    @Override
	    public boolean equals(Object usuario) {
	        return this == usuario || (usuario instanceof Usuario && Username.equals(((Usuario) usuario).Username));
	    }

	    @Override
	    public int compareTo(Usuario usuario) {
	        return Username.compareTo(usuario.Username);
	    }

	    @Override
	    public String toString() {
	        return
	                "Usuario               : " + Username;
	    }

	    
	    public String getUsername() {
	        return this.Username;
	    }
	    
	    public String getPassword() {
	        return this.Password;
	    }

}
