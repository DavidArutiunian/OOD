#include <algorithm>
#include <functional>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

using Strategy = function<void()>;

class Context
{
public:
    void PerformOperation()
    {
        for (const auto& strategy : m_strategies)
        {
            strategy();
        }
    }

    void SetStrategies(vector<Strategy>&& strategies)
    {
        m_strategies = strategies;
    }

private:
    vector<Strategy> m_strategies;
};

function<void()> FlyWithWings()
{
    size_t flightsCount = 0;
    return [=]() mutable {
        flightsCount++;
        cout << "I'm flying with wings!!" << endl;
        cout << flightsCount << " times flown..." << endl;
    };
};

void QuackBehavior()
{
    cout << "Quack Quack!!!" << endl;
}

void SqueakBehavior()
{
    cout << "Squeek!!!" << endl;
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

int main()
{
    Context ctx;

    // MallardDuck
    function<void()> Display = []() {
        cout << "I'm mallard duck" << endl;
    };
    ctx.SetStrategies({ Display, QuackBehavior, FlyWithWings(), DanceWaltzBehavior });
    ctx.PerformOperation();

    cout << endl;

    // RedheadDuck
    Display = []() {
        cout << "I'm redhead duck" << endl;
    };
    ctx.SetStrategies({ Display, QuackBehavior, FlyWithWings(), DanceMinuetBehavior });
    ctx.PerformOperation();

    cout << endl;

    // DecoyDuck
    Display = []() {
        cout << "I'm decoy duck" << endl;
    };
    ctx.SetStrategies({ Display });
    ctx.PerformOperation();

    cout << endl;

    // RubberDuck
    Display = []() {
        cout << "I'm rubber duck" << endl;
    };
    ctx.SetStrategies({ Display, SqueakBehavior });
    ctx.PerformOperation();

    cout << endl;

    // ModelDuck
    Display = []() {
        cout << "I'm model duck" << endl;
    };
    ctx.SetStrategies({ Display, QuackBehavior });
    ctx.PerformOperation();

    cout << endl;

    ctx.SetStrategies({ Display, QuackBehavior, FlyWithWings() });
    ctx.PerformOperation();
}
