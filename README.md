# Scalajs+

This repository contains handy macros , facades for upcoming javascript features(not finalized yet), polyfills when they make sense and some useful misc stuff.

```scala
// build.sbt
resolvers += Resolver.bintrayRepo("scalajs-plus", "maven")
libraryDependencies += "scalajs-plus" %%% "core" % "replaceThisWithLatestVersionNumberFromReleaseTags"
//works with scalajs >= 1.0.0-M2 and supports only scala 2.12
```


# Docs 

- [Macros](#macros)
- [Facades](#facades)
- [Polyfills](#polyfills)
- [Misc](#misc)


# Macros

- [FunctionObjectMacro](#FunctionObjectMacro)

### FunctionObjectMacro

  converts scala function parameters to javascript literal object.
  
```scala

  def point(x:Int,y:Int,z:js.UndefOr[Int] = js.undefined) : js.Object = {
    val p = FunctionObjectMacro()
    p.asInstanceOf[js.Object]
  }
  
  val p = point(1,1)  // {x:1,y:1} 
  
  val p = point(1,1,1) // {x:1,y:1,z:1}

```  

 ***Renaming Params*** 
 
 ```scala
 
   def voltage(i:Double,@rename("resistance")r:Double) : js.Object = {
     val v = FunctionObjectMacro()
     v.asInstanceOf[js.Object]
   }
   
   val v1 = volate(i = 2,r = 100) // {i: 2,resistance:100}
 ```

***Excluding Params***

```scala
 
 def energy(@rename("mass") m:Double,c:Double,@exclude debug:Boolean):js.Object = {
    if(debug) // do something
    val e = FunctionObjectMacro()
    e.asInstanceOf[js.Object]
 }
 
 val e = energy(m = 74,c = 3.18,debug = false) // {mass:74,c:3.18}
 
``` 

# Facades 

- [Observable](#obserbale)


### Observable 

  Facade for :https://github.com/tc39/proposal-observable (Ref Impl : https://github.com/zenparsing/zen-observable)

# Polyfills 

- [Object.assign](#object-assign)


### Object Assign

 Polyfill for [js.Object.assign](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/assign)
 
 ***Example***
 ```scala
    val target = js.Dynamic.literal(id = 1, name = "hello")
    val source1 = js.Dynamic.literal(id = 2, name2 = "hello2")
    val source2 = js.Dynamic.literal(name = "hello3", age = 20)
    
    val combine = ObjectAssign(target, source1, source2) // {id:2,name:"hello3",name2:"hello2",age:20}
```


# Misc 

- [OptionalParam](#optionalparam)
- [ReadonlyArray](#readonlyarray)
- [UnionTypeToJSAnyImplicit](#uniontype-to-jsany-implicit)
- [UndefNullOr](#undefnullor)

### OptionalParam

 Inline version of js.UndefOr[] , mostly used in conjunction with FunctionObjectMacro. More Details : https://github.com/scala-js/scala-js/issues/2714

 ***Example*** 
 
```scala
 def fun(value: OptionalParam[Int] = OptDefault) = value

    expect(fun(2)).toEqual(OptSpecified(2).asInstanceOf[js.Any])
    expect(fun()).toBe(OptDefault)

``` 

### ReadonlyArray 

 Readonly version of `js.Array` 
 
 
***Example***

```scala

val a:ReadonlyArray[Int] = ReadonlyArray(1,2,3)

a.length //compiles fine 

a.push() // coompile error 
``` 
 
 
 ### UnionType To JSAny Implicit

  Blindly casts scalajs union type to `js.Any`
  
  
***Example***

```scala
 
 val id : String | Int = 3
 
 val o = js.Dynamic.literal(id = id) // compile error 
 
  import scalajsplus.DangerousUnionToJSAnyImplicit._ //error gone 
  
  //Nit : make sure that scope of this import as least as possible

```   

 

### UndefNullOr

when interop with js world, if you want to treat undefined/null as empty use `UndefNullOr` type 

```scala
  trait Data extends js.Object {
    val x: UndefNullOr[Int]
    val y: UndefNullOr[String]
    val z: UndefNullOr[Double]
  }
  val data = json(x = js.undefined, y = null, z = 1.0).asInstanceOf[Data]

```

