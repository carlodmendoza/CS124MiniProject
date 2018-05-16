package maze;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class MazeIntercept {
	
	public Object run(Class clazz) throws Exception {
		ByteBuddy byteBuddy = new ByteBuddy();
		
		DynamicType.Builder<Object> builder = byteBuddy.subclass(clazz).implement(EnterCondition.class);

		builder = builder.name(clazz.getName() + "$ByteBuddy$").serialVersionUid((long) clazz.getName().hashCode());
		builder = builder.method(ElementMatchers.named("canEnter")).intercept(MethodDelegation.to(MazeInterceptor.class));		
		builder = builder.method(ElementMatchers.named("enterMessage")).intercept(MethodDelegation.to(MazeInterceptor.class));
		builder = builder.method(ElementMatchers.named("unableToEnterMessage")).intercept(MethodDelegation.to(MazeInterceptor.class));
	    
		Unloaded<Object> unloadedClass = builder.make();
				  
		Loaded<?> loaded = unloadedClass.load(getClass().getClassLoader());
		Class<?> dynamicType = loaded.getLoaded();
				 
		Object o = dynamicType.newInstance();
		return o;
	}
}
