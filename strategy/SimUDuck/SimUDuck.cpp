#include <cassert>
#include <iostream>
#include <memory>
#include <vector>

using namespace std;

struct IFlyBehavior
{
    virtual ~IFlyBehavior(){};
    virtual void Fly() = 0;
};

class FlyWithWings : public IFlyBehavior
{
public:
    void Fly() override
    {
        IncrementFlightCount();
        cout << "I'm flying with wings!!" << endl;
        cout << m_flightsCount << " times flown..." << endl;
    }

private:
    size_t m_flightsCount;

    void IncrementFlightCount()
    {
        m_flightsCount++;
    }
};

class FlyNoWay : public IFlyBehavior
{
public:
    void Fly() override {}
};

struct IQuackBehavior
{
    virtual ~IQuackBehavior(){};
    virtual void Quack() = 0;
};

class QuackBehavior : public IQuackBehavior
{
public:
    void Quack() override
    {
        cout << "Quack Quack!!!" << endl;
    }
};

class SqueakBehavior : public IQuackBehavior
{
public:
    void Quack() override
    {
        cout << "Squeek!!!" << endl;
    }
};

class MuteQuackBehavior : public IQuackBehavior
{
public:
    void Quack() override {}
};

struct IDanceBehavior
{
    virtual ~IDanceBehavior(){};
    virtual void Dance() = 0;
};

class DanceBehavior : public IDanceBehavior
{
public:
    void Dance() override
    {
        cout << "Dance Dance!!!" << endl;
    }
};

class NoDanceBehavior : public IDanceBehavior
{
public:
    void Dance() override
    {
    }
};

class DanceWaltzBehavior : public IDanceBehavior
{
public:
    void Dance() override
    {
        cout << "Dance Waltz!!!" << endl;
    }
};

class DanceMinuetBehavior : public IDanceBehavior
{
    void Dance() override
    {
        cout << "Dance Minuet!!!" << endl;
    }
};

class Duck
{
public:
    Duck(unique_ptr<IFlyBehavior>&& flyBehavior,
        unique_ptr<IQuackBehavior>&& quackBehavior,
        unique_ptr<IDanceBehavior>&& danceBehavior)
        : m_quackBehavior(move(quackBehavior))
        , m_danceBehavior(move(danceBehavior))
    {
        SetFlyBehavior(move(flyBehavior));
        assert(m_quackBehavior);
        assert(m_danceBehavior);
    }

    void Quack() const
    {
        m_quackBehavior->Quack();
    }

    void Swim()
    {
        cout << "I'm swimming" << endl;
    }

    void Fly()
    {
        m_flyBehavior->Fly();
    }

    void Dance() const
    {
        m_danceBehavior->Dance();
    }

    void SetFlyBehavior(unique_ptr<IFlyBehavior>&& flyBehavior)
    {
        assert(flyBehavior);
        m_flyBehavior = move(flyBehavior);
    }

    virtual void Display() const = 0;
    virtual ~Duck() = default;

private:
    unique_ptr<IFlyBehavior> m_flyBehavior;
    unique_ptr<IQuackBehavior> m_quackBehavior;
    unique_ptr<IDanceBehavior> m_danceBehavior;
};

class MallardDuck : public Duck
{
public:
    MallardDuck()
        : Duck(make_unique<FlyWithWings>(), make_unique<QuackBehavior>(), make_unique<DanceWaltzBehavior>())
    {
    }

    void Display() const override
    {
        cout << "I'm mallard duck" << endl;
    }
};

class RedheadDuck : public Duck
{
public:
    RedheadDuck()
        : Duck(make_unique<FlyWithWings>(), make_unique<QuackBehavior>(), make_unique<DanceMinuetBehavior>())
    {
    }
    void Display() const override
    {
        cout << "I'm redhead duck" << endl;
    }
};

class DecoyDuck : public Duck
{
public:
    DecoyDuck()
        : Duck(make_unique<FlyNoWay>(), make_unique<MuteQuackBehavior>(), make_unique<NoDanceBehavior>())
    {
    }

    void Display() const override
    {
        cout << "I'm decoy duck" << endl;
    }
};

class RubberDuck : public Duck
{
public:
    RubberDuck()
        : Duck(make_unique<FlyNoWay>(), make_unique<SqueakBehavior>(), make_unique<NoDanceBehavior>())
    {
    }

    void Display() const override
    {
        cout << "I'm rubber duck" << endl;
    }
};

class ModelDuck : public Duck
{
public:
    ModelDuck()
        : Duck(make_unique<FlyNoWay>(), make_unique<QuackBehavior>(), make_unique<NoDanceBehavior>())
    {
    }

    void Display() const override
    {
        cout << "I'm model duck" << endl;
    }
};

void DrawDuck(Duck const& duck)
{
    duck.Display();
}

void PlayWithDuck(Duck& duck)
{
    DrawDuck(duck);
    duck.Quack();
    duck.Fly();
    duck.Dance();
    cout << endl;
}

void main()
{
    MallardDuck mallardDuck;
    PlayWithDuck(mallardDuck);

    RedheadDuck redheadDuck;
    PlayWithDuck(redheadDuck);

    RubberDuck rubberDuck;
    PlayWithDuck(rubberDuck);

    DecoyDuck decoyDuck;
    PlayWithDuck(decoyDuck);

    ModelDuck modelDuck;
    PlayWithDuck(modelDuck);
    modelDuck.SetFlyBehavior(make_unique<FlyWithWings>());
    PlayWithDuck(modelDuck);
}
