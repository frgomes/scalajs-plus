package scalajsplus

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => json}
import scala.util.Try
class UndefNullOrTest extends BaseTest {

  trait Data extends js.Object {
    val x: UndefNullOr[Int]
    val y: UndefNullOr[String]
    val z: UndefNullOr[Double]
  }
  val data = json(x = js.undefined, y = null, z = 1.0).asInstanceOf[Data]

  test("get") {

    expect(Try(data.x.get).isFailure).toBeTruthy()
    expect(Try(data.y.get).isFailure).toBeTruthy()
    expect(data.z.get).toBe(1)
  }

  test("getOrElse") {
    expect(data.x.getOrElse(2)).toBe(2)
    expect(data.y.getOrElse("hello")).toBe("hello")
    expect(data.z.getOrElse(2.0)).toBe(1)
  }

  test("isEmpty") {
    expect(data.x.isEmpty).toBeTruthy()
    expect(data.y.isEmpty).toBeTruthy()
    expect(data.z.isEmpty).toBeFalsy()
  }

  test("isDefined") {
    expect(data.x.isDefined).toBeFalsy()
    expect(data.y.isDefined).toBeFalsy()
    expect(data.z.isDefined).toBeTruthy()
  }

  test("map") {
    expect(data.x.map(i => i + 1))
      .toBe(null)
    expect(data.y.map(i => i + "extra"))
      .toBe(null)
    expect(data.z.map(d => d + 1).get).toBe(2)
  }

  test("fold") {
    expect(data.x.fold(2)(i => i + 1)).toBe(2)
    expect(data.y.fold("default")(i => i + "extra")).toBe("default")
    expect(data.z.fold(0.0)(d => d + 1.0)).toBe(2.0)
  }

  test("nonEmpty") {
    expect(data.x.nonEmpty).toBeFalsy()
    expect(data.y.nonEmpty).toBeFalsy()
    expect(data.z.nonEmpty).toBeTruthy()
  }

  test("contains") {
    expect(data.x.contains(1)).toBeFalsy()
    expect(data.y.contains("hello")).toBeFalsy()
    expect(data.z.contains(1.0)).toBeTruthy()
  }

  test("exists") {
    expect(data.x.exists(i => i == 1)).toBeFalsy()
    expect(data.y.exists(i => i == "hello")).toBeFalsy()
    expect(data.z.exists(d => d == 1.0)).toBeTruthy()
    expect(data.z.exists(d => d == 2.0)).toBeFalsy()

  }

  test("foreach") {
    var x = 0
    data.x.foreach(i => x = i)
    expect(x).toBe(0)
    var y = ""
    data.y.foreach(s => y = s)
    expect(y).toBe("")
    var z = 0.0
    data.z.foreach(d => z = d)
    expect(z).toBe(1.0)
  }

  test("toOption") {
    expect(data.x.toOption).toBe(None)
    expect(data.y.toOption).toBe(None)
    expect(data.z.toOption).toMatchObject(Some(1.0))
  }
}
