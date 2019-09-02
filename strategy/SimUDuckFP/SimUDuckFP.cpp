#include <algorithm>
#include <functional>
#include <iostream>
#include <memory>
#include <string>
#include <vector>

using namespace std;

using Strategy = function<void()>;

class Context
{
public:
    void PerformQuackOperation()
    {
        if (m_quackStrategy)
        {
            m_quackStrategy();
        }
    }

    void PerformFlyOperation()
    {
        if (m_flyStrategy)
        {
            m_flyStrategy();
        }
    }

    void PerformDanceOperation()
    {
        if (m_danceStrategy)
        {
            m_danceStrategy();
        }
    }

    void SetQuackStrategy(Strategy&& strategy)
    {
        m_quackStrategy = move(strategy);
    }

    void SetFlyStrategy(Strategy&& strategy)
    {
        m_flyStrategy = move(strategy);
    }

    void SetDanceStrategy(Strategy&& strategy)
    {
        m_danceStrategy = move(strategy);
    }

private:
    Strategy m_quackStrategy;
    Strategy m_flyStrategy;
    Strategy m_danceStrategy;
};

function<void()> FlyWithWings()
{
    std::size_t flightsCount = 0;
    return [=]() mutable {
        flightsCount++;
        cout << "I'm flying with wings!!" << endl;
        cout << flightsCount << " times flown..." << endl;
    };
};

void FlyNoWay()
{
}

void QuackBehavior()
{
    cout << "Quack Quack!!!" << endl;
}

void SqueakBehavior()
{
    cout << "Squeek!!!" << endl;
}

void MuteQuackBehavior()
{
}

void DanceBehavior()
{
    cout << "Dance Dance!!!" << endl;
}

void DanceWaltzBehavior()
{
    cout << "Dance Waltz!!!" << endl;
}

void DanceMinuetBehavior()
{
    cout << "Dance Minuet!!!" << endl;
}

void NoDanceBehavior()
{
}

class Duck
{
public:
    Duck(unique_ptr<Context>&& ctx)
    {
        m_ctx = move(ctx);
    }

    void Quack() const
    {
        m_ctx->PerformQuackOperation();
    }

    void Swim()
    {
        cout << "I'm swimming" << endl;
    }

    void Fly()
    {
        m_ctx->PerformFlyOperation();
    }

    void Dance() const
    {
        m_ctx->PerformDanceOperation();
    }

    virtual void Display() const = 0;
    virtual ~Duck() = default;

private:
    unique_ptr<Context> m_ctx;
};

class MallardDuck : public Duck
{
public:
    MallardDuck(unique_ptr<Context>&& ctx)
        : Duck(move(ctx))
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
    RedheadDuck(unique_ptr<Context>&& ctx)
        : Duck(move(ctx))
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
    DecoyDuck(unique_ptr<Context>&& ctx)
        : Duck(move(ctx))
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
    RubberDuck(unique_ptr<Context>&& ctx)
        : Duck(move(ctx))
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
    ModelDuck(unique_ptr<Context>&& ctx)
        : Duck(move(ctx))
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

int main()
{
    Context ctx;

    ctx.SetFlyStrategy(FlyWithWings);
    ctx.SetQuackStrategy(QuackBehavior);

    ctx.SetDanceStrategy(DanceWaltzBehavior);
    MallardDuck mallardDuck(move(make_unique<Context>(ctx)));
    PlayWithDuck(mallardDuck);

    ctx.SetDanceStrategy(DanceMinuetBehavior);
    RedheadDuck redheadDuck(move(make_unique<Context>(ctx)));
    PlayWithDuck(redheadDuck);

    ctx.SetFlyStrategy(FlyNoWay);
    ctx.SetQuackStrategy(SqueakBehavior);
    ctx.SetDanceStrategy(NoDanceBehavior);
    RubberDuck rubberDuck(move(make_unique<Context>(ctx)));
    PlayWithDuck(rubberDuck);

    ctx.SetFlyStrategy(FlyNoWay);
    ctx.SetQuackStrategy(MuteQuackBehavior);
    ctx.SetDanceStrategy(NoDanceBehavior);
    DecoyDuck decoyDuck(move(make_unique<Context>(ctx)));
    PlayWithDuck(decoyDuck);

    ctx.SetFlyStrategy(FlyNoWay);
    ctx.SetQuackStrategy(QuackBehavior);
    ctx.SetDanceStrategy(NoDanceBehavior);
    ModelDuck modelDuck(move(make_unique<Context>(ctx)));
    PlayWithDuck(modelDuck);
}
